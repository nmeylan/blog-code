package ch.nmeylan.blog.example;


import com.github.f4b6a3.uuid.UuidCreator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1, time = 15, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 20, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(0)
@Threads(5)
public class ItemRepositoryBenchmark {

    @Param({"100"})
    public int batchSize;

    @State(Scope.Benchmark)
    public static class BenchmarkContext extends AbstractBenchmarkContext {
        private ItemRepositoryImpl itemRepository;
        private Random random;

        static {
            setup(BenchmarkApplication.class);
        }

        @Setup
        public void setup() {
            // Getting the bean we want to test
            itemRepository = appContext.getBean(ItemRepositoryImpl.class);
            random = new Random();
        }
    }


    @Benchmark
    public void batchInsert_JdbcTemplateNoType(BenchmarkContext context) {
        ArrayList<ItemEntity> items = generateItems(context);
        context.itemRepository.batchInsert_JdbcTemplateNoType(items);
    }
    @Benchmark
    public void batchInsert_JdbcTemplateWithType(BenchmarkContext context) {
        ArrayList<ItemEntity> items = generateItems(context);
        context.itemRepository.batchInsert_JdbcTemplateWithType(items);
    }

    private ArrayList<ItemEntity> generateItems(BenchmarkContext context) {
        ArrayList<ItemEntity> items = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize - 1; i++) {
            items.add(new ItemEntity().setId( UuidCreator.getTimeOrderedEpoch().toString())
                    .setNameAegis(ItemFixtures.ITEMS_AEGIS[context.random.nextInt(ItemFixtures.ITEMS_AEGIS.length)])
                    .setNameEnglish(ItemFixtures.ITEMS[context.random.nextInt(ItemFixtures.ITEMS.length)])
                    .setRange(context.random.nextInt(0, 5))
                    .setDefense(context.random.nextInt(1, 9))
                    .setAttack(context.random.nextInt(1, 9))
                    .setSlots(context.random.nextInt(0, 2))
                    .setPriceBuy(context.random.nextInt(2000, 80000))
                    .setPriceSell(context.random.nextInt(2000, 80000))
                    .setType(ItemFixtures.ITEM_TYPES[context.random.nextInt(ItemFixtures.ITEM_TYPES.length)])
                    .setSubType(ItemFixtures.ITEM_TYPES[context.random.nextInt(ItemFixtures.ITEM_TYPES.length)])
                    .setJobAll(context.random.nextBoolean())
                    .setJobBarddancer(context.random.nextBoolean())
                    .setJobBlacksmith(context.random.nextBoolean())
                    .setJobCrusader(context.random.nextBoolean())
                    .setJobGunslinger(context.random.nextBoolean())
                    .setJobHunter(context.random.nextBoolean())
                    .setJobKnight(context.random.nextBoolean())
                    .setJobMage(context.random.nextBoolean())
                    .setJobMerchant(context.random.nextBoolean())
                    .setJobMonk(context.random.nextBoolean())
                    .setJobNinja(context.random.nextBoolean())
                    .setJobNovice(context.random.nextBoolean())
                    .setJobPriest(context.random.nextBoolean())
                    .setJobRogue(context.random.nextBoolean())
                    .setJobSage(context.random.nextBoolean())
                    .setJobSoullinker(context.random.nextBoolean())
                    .setJobStargladiator(context.random.nextBoolean())
                    .setJobSupernovice(context.random.nextBoolean())
                    .setJobSwordman(context.random.nextBoolean())
                    .setJobTaekwon(context.random.nextBoolean())
                    .setJobThief(context.random.nextBoolean())
                    .setJobWizard(context.random.nextBoolean())

            );
        }
        items.add(new ItemEntity().setId( UuidCreator.getTimeOrderedEpoch().toString())
                    .setNameAegis(null)
                    .setNameEnglish(ItemFixtures.ITEMS[context.random.nextInt(ItemFixtures.ITEMS.length)])
                    .setRange(context.random.nextInt(0, 5))
                    .setDefense(context.random.nextInt(1, 9))
                    .setAttack(context.random.nextInt(1, 9))
                    .setSlots(context.random.nextInt(0, 2))
                    .setPriceBuy(context.random.nextInt(2000, 80000))
                    .setPriceSell(null)
                    .setType(ItemFixtures.ITEM_TYPES[context.random.nextInt(ItemFixtures.ITEM_TYPES.length)])
                    .setSubType(ItemFixtures.ITEM_TYPES[context.random.nextInt(ItemFixtures.ITEM_TYPES.length)])
                    .setJobAll(context.random.nextBoolean())

            );
        return items;
    }

    public static void main(String[] args) throws RunnerException {
        // Configuration JMH
        Options opt = new OptionsBuilder()
                .include(ItemRepositoryBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
