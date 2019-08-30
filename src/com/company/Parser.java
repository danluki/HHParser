package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java&page=";
    private static String url;
    private static Vacancy premiumVacancy = new Vacancy();
    private static Vacancy simpleVacancy = new Vacancy();
    private static int pageNumber = 0;

    public Parser() throws IOException {
        ConsoleHelper.writeMessage("Введите название вакансии");
        String jobName = ConsoleHelper.readString();
        url = URL_FORMAT.replaceAll("(?<==).*(?=&)", jobName).replaceAll(" ", "");
        while(true) {
            try {
                Document html = getHeadHunterHtml();
                premiumVacancy.elements = html
                        .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy vacancy-serp__vacancy_premium");
                simpleVacancy.elements = html
                        .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                parseVacancies(premiumVacancy);
                parseVacancies(simpleVacancy);
            }
            catch (NullPointerException | IOException e){
                break;

            }
        }
    }

    private void parseVacancies(Vacancy vacancy){
        for(Element element : vacancy.elements){
            Vacancy vac = new Vacancy();

            Element name = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").first();
            String n = name == null ? "Не указано" : name.text();
            vac.setName(n);

            Element company = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").first();
            String c = company == null ? "Не указано" : company.text();
            vac.setCompany(c);

            Element responsibility = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_responsibility").first();
            String r = responsibility == null ? "Не указано" : responsibility.text();
            vac.setResponsibility(r);

            Element requirement = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_requirement").first();
            String re = requirement == null ? "Не указано" : requirement.text();
            vac.setRequirement(re);

            Element salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").first();
            String s = salary == null ? "Не указано" : salary.text();
            vac.setSalary(s);

            vacancy.vacancies.add(vac);
        }
    }

    private Document getHeadHunterHtml() throws IOException{
        String u = url + pageNumber;
        ConsoleHelper.writeMessage(u);
        Document doc = Jsoup.connect(u).userAgent("Chrome/57.0.2987.133 (jsoup)").referrer("?").get();
        System.out.println("Страница получена!");
        pageNumber++;
        return doc;
    }

    public static Vacancy getPremiumVacancy() {
        return premiumVacancy;
    }

    public static Vacancy getSimpleVacancy() {
        return simpleVacancy;
    }
}
