package com.company;

public class View {
    public static void Show(){
        System.out.println("Начинаем сбор вакансий....");
        showPremiumVacancies();
        showSimpleVacancies();
        ConsoleHelper.writeMessage("Анализ завершён.");
        int sum = Parser.getPremiumVacancy().vacancies.size() + Parser.getSimpleVacancy().vacancies.size();
        ConsoleHelper.writeMessage("Найдено вакансий: " + sum);
    }

    private static void showPremiumVacancies(){
        ConsoleHelper.writeMessage("Премиум вакансии: ");
        ConsoleHelper.writeMessage("------------------------------");
        for(Vacancy vacancy : Parser.getPremiumVacancy().vacancies){
            ConsoleHelper.writeMessage(vacancy.getName());
            ConsoleHelper.writeMessage(vacancy.getCompany());
            ConsoleHelper.writeMessage(vacancy.getResponsibility());
            ConsoleHelper.writeMessage(vacancy.getRequirement());
            ConsoleHelper.writeMessage(vacancy.getSalary());
            ConsoleHelper.writeMessage("------------------------------");
        }
    }

    private static void showSimpleVacancies(){
        ConsoleHelper.writeMessage("Простые вакансии: ");
        ConsoleHelper.writeMessage("------------------------------");
        for(Vacancy vacancy : Parser.getSimpleVacancy().vacancies){
            ConsoleHelper.writeMessage(vacancy.getName());
            ConsoleHelper.writeMessage(vacancy.getCompany());
            ConsoleHelper.writeMessage(vacancy.getResponsibility());
            ConsoleHelper.writeMessage(vacancy.getRequirement());
            ConsoleHelper.writeMessage(vacancy.getSalary());
            ConsoleHelper.writeMessage("------------------------------");
        }
    }
}
