package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AihealueDao;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        AihealueDao aihealueDao = new AihealueDao(database);
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealueet", aihealueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        get("/aihealueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealue", aihealueDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "aihealue");
        }, new ThymeleafTemplateEngine());
        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
}
