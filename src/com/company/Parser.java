package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final String URL = "https://hh.ru/search/vacancy?L_is_autosearch=false&area=1&clusters=true&enable_snippets=true&text=java&page=";
    private static Document html;
    private static Elements premiumVacanciesElements;
    private static Elements simpleVacanciesElements;
    private static List<Vacancy> premiumVacancies = new ArrayList<>();
    private static List<Vacancy> simpleVacancies = new ArrayList<>();
    private static int pageNumber = 0;
    public Parser(){

        while(true) {
            try {
                html = getHeadHunterHtml();
                premiumVacanciesElements = html.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy vacancy-serp__vacancy_premium");
                simpleVacanciesElements = html.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                parsePremiumVacancies();
                parseSimpleVacancies();
            }
            catch (NullPointerException | IOException e){
                break;
            }
        }
    }

    private void parsePremiumVacancies(){
        for(int i = 0; i < premiumVacanciesElements.size(); i++){
            Vacancy premiumVacancy = new Vacancy();
            premiumVacancy.setName(premiumVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").get(0).text());

            try{
                premiumVacancy.setCompany(premiumVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").get(0).text());
            }
            catch (IndexOutOfBoundsException e){
                premiumVacancy.setCompany("Не указана");
            }

            premiumVacancy.setResponsibility(premiumVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_responsibility").get(0).text());
            premiumVacancy.setRequirement(premiumVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_requirement").get(0).text());
            try {
                String salary = premiumVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").get(0).text();
                premiumVacancy.setSalary(salary);
            }
            catch (IndexOutOfBoundsException e){
                premiumVacancy.setSalary("Не указана");

            }
            premiumVacancies.add(premiumVacancy);
        }
    }

    private void parseSimpleVacancies(){
        for(int i = 0; i < simpleVacanciesElements.size(); i++){
            Vacancy simpleVacancy = new Vacancy();
            simpleVacancy.setName(simpleVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").get(0).text());
            try {
                simpleVacancy.setCompany(simpleVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").get(0).text());
            }
            catch (IndexOutOfBoundsException e){
                simpleVacancy.setCompany("Не указана");
            }
            try {
                String salary = simpleVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").get(0).text();
                simpleVacancy.setSalary(salary);
            }
            catch (IndexOutOfBoundsException e){
                simpleVacancy.setSalary("Не указана");

            }
            try{
                simpleVacancy.setResponsibility(simpleVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_responsibility").get(0).text());
            }
            catch (IndexOutOfBoundsException e){
                simpleVacancy.setResponsibility("Не указано");
            }
            try{
                simpleVacancy.setRequirement(simpleVacanciesElements.get(i).getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_requirement").get(0).text());
            }
            catch (IndexOutOfBoundsException e){
                simpleVacancy.setRequirement("Не указано");
            }
            simpleVacancies.add(simpleVacancy);
        }
    }

    private Document getHeadHunterHtml() throws IOException{
        String url = URL + pageNumber;
        System.out.println(url);
        Document doc = (Document) Jsoup.connect(url).userAgent("Chrome/57.0.2987.133 (jsoup)").referrer("?").get();
        System.out.println("Страница получена!");
        pageNumber++;
        return doc;
    }

    public static List<Vacancy> getPremiumVacancies() {
        return premiumVacancies;
    }

    public static void setPremiumVacancies(List<Vacancy> premiumVacancies) {
        Parser.premiumVacancies = premiumVacancies;
    }

    public static List<Vacancy> getSimpleVacancies() {
        return simpleVacancies;
    }

    public static void setSimpleVacancies(List<Vacancy> simpleVacancies) {
        Parser.simpleVacancies = simpleVacancies;
    }

}
