package ch.nmeylan.blog.example;

import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepositoryImpl {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public void batchInsert_JdbcTemplateNoType(List<ItemEntity> items) {
        String query = "INSERT INTO items ( id, name_aegis, name_english, type, sub_type, price_buy, price_sell, weight, defense, attack, range, slots, job_all, job_acolyte, job_archer, job_alchemist, job_assassin, job_barddancer, job_blacksmith, job_crusader, job_gunslinger, job_hunter, job_knight, job_mage, job_merchant, job_monk, job_ninja, job_novice, job_priest, job_rogue, job_sage, job_soullinker, job_stargladiator, job_supernovice, job_swordman, job_taekwon, job_thief, job_wizard ) VALUES  (:id, :name_aegis, :name_english, :type, :sub_type, :price_buy, :price_sell, :weight, :defense, :attack, :range, :slots, :job_all, :job_acolyte, :job_archer, :job_alchemist, :job_assassin, :job_barddancer, :job_blacksmith, :job_crusader, :job_gunslinger, :job_hunter, :job_knight, :job_mage, :job_merchant, :job_monk, :job_ninja, :job_novice, :job_priest, :job_rogue, :job_sage, :job_soullinker, :job_stargladiator, :job_supernovice, :job_swordman, :job_taekwon, :job_thief, :job_wizard )";
        SqlParameterSource[] batchParams = new SqlParameterSource[items.size()];
        int i = 0;
        for (ItemEntity entry : items) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", entry.getId());
            params.addValue("name_aegis", entry.getNameAegis());
            params.addValue("name_english", entry.getNameEnglish());
            params.addValue("type", entry.getType());
            params.addValue("sub_type", entry.getSubType());
            params.addValue("price_buy", entry.getPriceBuy());
            params.addValue("price_sell", entry.getPriceSell());
            params.addValue("weight", entry.getWeight());
            params.addValue("defense", entry.getDefense());
            params.addValue("attack", entry.getAttack());
            params.addValue("range", entry.getRange());
            params.addValue("slots", entry.getSlots());
            params.addValue("job_all", entry.getJobAll());
            params.addValue("job_acolyte", entry.getJobAcolyte());
            params.addValue("job_archer", entry.getJobArcher());
            params.addValue("job_alchemist", entry.getJobAlchemist());
            params.addValue("job_assassin", entry.getJobAssassin());
            params.addValue("job_barddancer", entry.getJobBarddancer());
            params.addValue("job_blacksmith", entry.getJobBlacksmith());
            params.addValue("job_crusader", entry.getJobCrusader());
            params.addValue("job_gunslinger", entry.getJobGunslinger());
            params.addValue("job_hunter", entry.getJobHunter());
            params.addValue("job_knight", entry.getJobKnight());
            params.addValue("job_mage", entry.getJobMage());
            params.addValue("job_merchant", entry.getJobMerchant());
            params.addValue("job_monk", entry.getJobMonk());
            params.addValue("job_ninja", entry.getJobNinja());
            params.addValue("job_novice", entry.getJobNovice());
            params.addValue("job_priest", entry.getJobPriest());
            params.addValue("job_rogue", entry.getJobRogue());
            params.addValue("job_sage", entry.getJobSage());
            params.addValue("job_soullinker", entry.getJobSoullinker());
            params.addValue("job_stargladiator", entry.getJobStargladiator());
            params.addValue("job_supernovice", entry.getJobSupernovice());
            params.addValue("job_swordman", entry.getJobSwordman());
            params.addValue("job_taekwon", entry.getJobTaekwon());
            params.addValue("job_thief", entry.getJobThief());
            params.addValue("job_wizard", entry.getJobWizard());
            batchParams[i++] = params;
        }
        namedParameterJdbcTemplate.batchUpdate(query, batchParams);
    }

    public void batchInsert_JdbcTemplateWithType(List<ItemEntity> items) {
        String query = "INSERT INTO items ( id, name_aegis, name_english, type, sub_type, price_buy, price_sell, weight, defense, attack, range, slots, job_all, job_acolyte, job_archer, job_alchemist, job_assassin, job_barddancer, job_blacksmith, job_crusader, job_gunslinger, job_hunter, job_knight, job_mage, job_merchant, job_monk, job_ninja, job_novice, job_priest, job_rogue, job_sage, job_soullinker, job_stargladiator, job_supernovice, job_swordman, job_taekwon, job_thief, job_wizard ) VALUES  ( :id, :name_aegis, :name_english, :type, :sub_type, :price_buy, :price_sell, :weight, :defense, :attack, :range, :slots, :job_all, :job_acolyte, :job_archer, :job_alchemist, :job_assassin, :job_barddancer, :job_blacksmith, :job_crusader, :job_gunslinger, :job_hunter, :job_knight, :job_mage, :job_merchant, :job_monk, :job_ninja, :job_novice, :job_priest, :job_rogue, :job_sage, :job_soullinker, :job_stargladiator, :job_supernovice, :job_swordman, :job_taekwon, :job_thief, :job_wizard )";
        SqlParameterSource[] batchParams = new SqlParameterSource[items.size()];
        int i = 0;
        for (ItemEntity entry : items) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", entry.getId(), SqlTypes.VARCHAR);
            params.addValue("name_aegis", entry.getNameAegis(), SqlTypes.VARCHAR);
            params.addValue("name_english", entry.getNameEnglish(), SqlTypes.VARCHAR);
            params.addValue("type", entry.getType(), SqlTypes.VARCHAR);
            params.addValue("sub_type", entry.getSubType(), SqlTypes.VARCHAR);
            params.addValue("price_buy", entry.getPriceBuy(), SqlTypes.INTEGER);
            params.addValue("price_sell", entry.getPriceSell(), SqlTypes.INTEGER);
            params.addValue("weight", entry.getWeight(), SqlTypes.INTEGER);
            params.addValue("defense", entry.getDefense(), SqlTypes.INTEGER);
            params.addValue("attack", entry.getAttack(), SqlTypes.INTEGER);
            params.addValue("range", entry.getRange(), SqlTypes.INTEGER);
            params.addValue("slots", entry.getSlots(), SqlTypes.INTEGER);
            params.addValue("job_all", entry.getJobAll(), SqlTypes.BOOLEAN);
            params.addValue("job_acolyte", entry.getJobAcolyte(), SqlTypes.BOOLEAN);
            params.addValue("job_archer", entry.getJobArcher(), SqlTypes.BOOLEAN);
            params.addValue("job_alchemist", entry.getJobAlchemist(), SqlTypes.BOOLEAN);
            params.addValue("job_assassin", entry.getJobAssassin(), SqlTypes.BOOLEAN);
            params.addValue("job_barddancer", entry.getJobBarddancer(), SqlTypes.BOOLEAN);
            params.addValue("job_blacksmith", entry.getJobBlacksmith(), SqlTypes.BOOLEAN);
            params.addValue("job_crusader", entry.getJobCrusader(), SqlTypes.BOOLEAN);
            params.addValue("job_gunslinger", entry.getJobGunslinger(), SqlTypes.BOOLEAN);
            params.addValue("job_hunter", entry.getJobHunter(), SqlTypes.BOOLEAN);
            params.addValue("job_knight", entry.getJobKnight(), SqlTypes.BOOLEAN);
            params.addValue("job_mage", entry.getJobMage(), SqlTypes.BOOLEAN);
            params.addValue("job_merchant", entry.getJobMerchant(), SqlTypes.BOOLEAN);
            params.addValue("job_monk", entry.getJobMonk(), SqlTypes.BOOLEAN);
            params.addValue("job_ninja", entry.getJobNinja(), SqlTypes.BOOLEAN);
            params.addValue("job_novice", entry.getJobNovice(), SqlTypes.BOOLEAN);
            params.addValue("job_priest", entry.getJobPriest(), SqlTypes.BOOLEAN);
            params.addValue("job_rogue", entry.getJobRogue(), SqlTypes.BOOLEAN);
            params.addValue("job_sage", entry.getJobSage(), SqlTypes.BOOLEAN);
            params.addValue("job_soullinker", entry.getJobSoullinker(), SqlTypes.BOOLEAN);
            params.addValue("job_stargladiator", entry.getJobStargladiator(), SqlTypes.BOOLEAN);
            params.addValue("job_supernovice", entry.getJobSupernovice(), SqlTypes.BOOLEAN);
            params.addValue("job_swordman", entry.getJobSwordman(), SqlTypes.BOOLEAN);
            params.addValue("job_taekwon", entry.getJobTaekwon(), SqlTypes.BOOLEAN);
            params.addValue("job_thief", entry.getJobThief(), SqlTypes.BOOLEAN);
            params.addValue("job_wizard", entry.getJobWizard(), SqlTypes.BOOLEAN);
            batchParams[i++] = params;
        }
        namedParameterJdbcTemplate.batchUpdate(query, batchParams);
    }
}
