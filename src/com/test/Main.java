package com.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
         Game.startScreen();
         long startTime = System.currentTimeMillis();
         Finance finance = new Finance();
         Game game = new Game();
         int countDiceRun = game.getRound();
         int startMoney = game.getStartMoney();
            Player humanPlayer = new Player(startMoney);
            Player computerPlayer = new Player(startMoney);
            Statistics statistics = new Statistics();
            int i=1;
              while (i<=countDiceRun ^ humanPlayer.getCredits()>0 ^ computerPlayer.getCredits()>0) {
               int playerResult = game.getDice();
               int computerResult = game.getDice();
               System.out.println("\nБросок кубика: " + i + "\nУ игрока: <" + playerResult + "> У компьютера: <" + computerResult + ">");
               int returnWinResult =game.getCompare(playerResult, computerResult);
               statistics.getRoundResult(returnWinResult);
               finance.calculateFinance(humanPlayer, computerPlayer, returnWinResult);
               statistics.writeStatistics(i, playerResult, computerResult, humanPlayer.getCredits(), computerPlayer.getCredits());
               i++;
        }
          statistics.getStatisticsInPercent();
           System.out.println("\n~~Программа выполнялась " + (Math.round((System.currentTimeMillis() - startTime) / 1000)) + " секунд~~");
        }
    }

class Player {
    private int credits;

    public Player(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}

class Finance{

    public void calculateFinance(Player humanPlayer, Player computerPlayer, int returnWinResult){
        if (returnWinResult == 1){   //human win
         humanPlayer.setCredits(humanPlayer.getCredits()+1);
         computerPlayer.setCredits(computerPlayer.getCredits()-1);
        }
        else if (returnWinResult==2){  //computer Win
            humanPlayer.setCredits(humanPlayer.getCredits()-1);
            computerPlayer.setCredits(computerPlayer.getCredits()+1);
        }
        System.out.println("[Финансы] ---> Человек: "+humanPlayer.getCredits()+" €  Компьютер: "+computerPlayer.getCredits()+" €");
    }
        }

class Game{
   static void startScreen(){
       System.out.println("Симулятор игры в кости. \nВ начале игры необходимо ввести количество раундов, а также количество денег (кредитов) у игроков\n Игра продолжается выбранное количество раундов, или же до тех пор, пока у одного из игроков не закончатся деньги");
   }
    public int getCompare(int playerResult, int computerResult) {
      int returnStatistics;
        if (playerResult > computerResult) {
            System.out.println("Игрок выиграл");
            returnStatistics = 1;
         } else if (playerResult < computerResult) {
            System.out.println("Компьютер выиграл");
            returnStatistics = 2;
        } else {
            System.out.println("Ничья");
            returnStatistics = 3;
        }
        return returnStatistics;
    }

    public static int getDice() {
       int tochka = (int) (2 + (Math.random() * 12));
        return tochka;
    }
    public int getRound(){
        System.out.println("Введите количество Раундов игры (бросков кубика): \n");
        Scanner kbdInput = new Scanner(System.in);
        int count = (int)kbdInput.nextInt();
        return count;
    }
    public int getStartMoney(){
        System.out.println("Введите сумму стартового капитала у каждого игрока : \n");
        Scanner kbdInput = new Scanner(System.in);
        int count = (int)kbdInput.nextInt();
        return count;
    }



}
class Statistics{
    private int playerWinResult;
    private int computerWinResult;
    private int nothingWinResult;

    public void getStatisticsInPercent()
    {
      if (playerWinResult<computerWinResult){
    System.out.println("-> В это раз на стороне компьютера была удача. С перевесом в " + (String.format("%.2f", ((1-((float)playerWinResult/(float)computerWinResult)))*100) +"%"));
     }
      else if (playerWinResult==computerWinResult){
    System.out.println("-> Удивительно, но ничья!");
}
       else {
    System.out.println("-> Человек победил c перевесом в " + (String.format("%.2f", ((1-((float)computerWinResult/(float)playerWinResult)))*100) +"%"));
}

    }
    public void getRoundResult(int statistics){
        switch (statistics) {
            case 1:
                playerWinResult++;
                break;
            case 2:
                computerWinResult++;
                break;
            case 3:
                nothingWinResult++;
                break;
        }
        System.out.println("\n<ИТОГ>\nИгрок выиграл: " + playerWinResult + " раз. Компьютер выиграл: " + computerWinResult + " . Ничья: " + nothingWinResult + " раз");

    }
    public void writeStatistics(int count, int playerResult, int computerResult, int humanPlayerCredits, int computerPlayerCredits){
        File file = new File("stat.txt");
                if(!file.exists()){
                    try {
                         file.createNewFile();
                    } catch (IOException e) {
                        System.out.println("Файл не создан");
                    }
                }
                else{
                    try {
                        FileWriter fileToWrite = new FileWriter(file, true);
                        fileToWrite.write("Бросок "+count+": "+" Человек: "+playerResult + " Компьютер: "+computerResult+" Общая статистика: <"+playerWinResult + " / " + computerWinResult + " / "+nothingWinResult+">"+" [Финансы] Человек: "+humanPlayerCredits+" Компьютер: "+computerPlayerCredits+" \n");
                        fileToWrite.close();
                    } catch (IOException e) {
                        System.out.println("Нет доступа к файлу");
                    }
                }
    }
}


