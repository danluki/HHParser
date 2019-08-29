package com.company;

public class View {
    public static void Show(){
        System.out.println("Начинаем сбор вакансий....");
        showPremiumVacancies();
        showSimpleVacancies();
        System.out.println("Анализ завершён.");
        int sum = Parser.getPremiumVacancies().size() + Parser.getSimpleVacancies().size();
        System.out.println("Найдено вакансий: " + sum);
    }

    private static void showPremiumVacancies(){
        System.out.println("Премиум вакансии: ");
        System.out.println("------------------------------");
        for(Vacancy vacancy : Parser.getPremiumVacancies()){
            System.out.println(vacancy.getName());
            System.out.println(vacancy.getCompany());
            System.out.println(vacancy.getResponsibility());
            System.out.println(vacancy.getRequirement());
            System.out.println(vacancy.getSalary());
            System.out.println("------------------------------");
        }
    }

    private static void showSimpleVacancies(){
        System.out.println("Простые вакансии: ");
        System.out.println("------------------------------");
        for(Vacancy vacancy : Parser.getSimpleVacancies()){
            System.out.println(vacancy.getName());
            System.out.println(vacancy.getCompany());
            System.out.println(vacancy.getResponsibility());
            System.out.println(vacancy.getRequirement());
            System.out.println(vacancy.getSalary());
            System.out.println("------------------------------");
        }
    }
}
