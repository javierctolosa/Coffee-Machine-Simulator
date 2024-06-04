package machine;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {

    private int waterMl;
    private int milkMl;
    private int coffeeGr;
    private int disposableCups;
    private int money;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.startService(scanner);
    }

    public CoffeeMachine() {
        this.waterMl = 400;
        this.milkMl = 540;
        this.coffeeGr = 120;
        this.disposableCups = 9;
        this.money = 550;
    }

    public CoffeeMachine(int machineWater, int machineMilk, int machineCoffee, int disposableCups, int machineMoney) {
        this.waterMl = Math.max(machineWater, 0);
        this.milkMl = Math.max(machineMilk, 0);
        this.coffeeGr = Math.max(machineCoffee, 0);
        this.disposableCups = Math.max(disposableCups, 0);
        this.money = Math.max(machineMoney, 0);
    }

    public void startService(Scanner scanner) {
        String action;
        do {
            action = this.getMenuAction(scanner);
            if (this.isMenuAction(action)) {
                switch (action) {
                    case "buy" -> this.buyAction(scanner);
                    case "fill" -> this.fillAction(scanner);
                    case "take" -> this.takeAction();
                    case "remaining" -> this.remainingAction();
                }
            } else {
                System.out.println("Please enter a valid menu option.");
            }
            System.out.println();
        } while (!action.equals("exit"));
    }

    public String getMenuAction(Scanner scanner) {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        return scanner.next().toLowerCase();
    }

    public Boolean isMenuAction(String action) {
        return new HashSet<>(List.of("buy", "fill", "take", "remaining", "exit")).contains(action);
    }

    public void remainingAction() {
        String message = String.format("""
                
                The coffee machine has:
                %1$d ml of water
                %2$d ml of milk
                %3$d g of coffee beans
                %4$d disposable cups
                $%5$d of money""", this.getWaterMl(), this.getMilkMl(), this.getCoffeeGr(), this.getDisposableCups(),
                this.getMoney());
        System.out.println(message);
    }

    public void buyAction(Scanner scanner) {
        String coffeeBuy;
        do {
            coffeeBuy = this.getCoffeeBuy(scanner);
            if (this.isCoffeeBuy(coffeeBuy)) {
                switch (coffeeBuy) {
                    case "1", "espresso" -> {
                        CoffeeCup espresso = new Espresso();
                        this.attemptMakeCoffee(espresso);
                    }
                    case "2", "latte" -> {
                        CoffeeCup latte = new Latte();
                        this.attemptMakeCoffee(latte);
                    }
                    case "3", "cappuccino" -> {
                        CoffeeCup cappuccino = new Cappuccino();
                        this.attemptMakeCoffee(cappuccino);
                    }
                }
                break;
            } else {
                System.out.println("Please select a valid option.");
            }
        } while (!coffeeBuy.equals("back"));
    }

    public String getCoffeeBuy(Scanner scanner) {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        return scanner.next().toLowerCase();
    }

    public Boolean isCoffeeBuy(String coffeeBuy) {
        return new HashSet<>(List.of("1", "espresso", "2", "latte", "3", "cappuccino", "back"))
                .contains(coffeeBuy);
    }

    public String validateSupplies(CoffeeCup coffeeCup) {
        return this.getWaterMl() - coffeeCup.getWaterMl() < 0 ? "Sorry, not enough water!"
        : this.getMilkMl() - coffeeCup.getMilkMl() < 0 ? "Sorry, not enough milk!"
        : this.getCoffeeGr() - coffeeCup.getCoffeeGr() < 0 ? "Sorry, not enough coffee!"
        : this.getDisposableCups() <= 0 ? "Sorry, not enough disposable cups!" : "true";
    }

    public void attemptMakeCoffee(CoffeeCup coffeeCup) {
        String suppliesMessage = this.validateSupplies(coffeeCup);
        if ("true".equals(suppliesMessage)) {
            this.makeCoffee(coffeeCup);
        } else {
            System.out.println(suppliesMessage);
        }
    }

    public void makeCoffee(CoffeeCup coffeeCup) {
        int newWater = this.getWaterMl() - coffeeCup.getWaterMl();
        this.setWaterMl(newWater);
        int newMilk = this.getMilkMl() - coffeeCup.getMilkMl();
        this.setMilkMl(newMilk);
        int newCoffee = this.getCoffeeGr() - coffeeCup.getCoffeeGr();
        this.setCoffeeGr(newCoffee);
        int newDisposableCups = this.getDisposableCups() - 1;
        this.setDisposableCups(newDisposableCups);

        int newMoney = this.getMoney() + coffeeCup.getPrice();
        this.setMoney(newMoney);

        System.out.println("I have enough resources, making you a coffee!");
    }

    public void fillAction(Scanner scanner) {
        System.out.println("\nWrite how many ml of water you want to add:");
        int inputWaterMl = scanner.nextInt();
        this.addWaterMl(inputWaterMl);

        System.out.println("Write how many ml of milk you want to add:");
        int inputMilkMl = scanner.nextInt();
        this.addMilkMl(inputMilkMl);

        System.out.println("Write how many grams of coffee beans you want to add:");
        int inputCoffeeGr = scanner.nextInt();
        this.addCoffeeGr(inputCoffeeGr);

        System.out.println("Write how many disposable cups you want to add:");
        int inputDisposableCups = scanner.nextInt();
        this.addDisposableCups(inputDisposableCups);
    }

    public void addWaterMl(int inputWaterMl) {
        inputWaterMl = Math.max(inputWaterMl, 0);
        int newWaterMl = inputWaterMl + this.getWaterMl();
        this.setWaterMl(newWaterMl);
    }

    public void addMilkMl(int inputMilkMl) {
        inputMilkMl = Math.max(inputMilkMl, 0);
        int newMilkMl = inputMilkMl + this.getMilkMl();
        this.setMilkMl(newMilkMl);
    }

    public void addCoffeeGr(int inputCoffeeGr) {
        inputCoffeeGr = Math.max(inputCoffeeGr, 0);
        int newCoffeeGr = inputCoffeeGr + this.getCoffeeGr();
        this.setCoffeeGr(newCoffeeGr);
    }

    public void addDisposableCups(int inputDisposableCups) {
        inputDisposableCups = Math.max(inputDisposableCups, 0);
        int newDisposableCups = inputDisposableCups + this.getDisposableCups();
        this.setDisposableCups(newDisposableCups);
    }

    public void takeAction() {
        System.out.printf("\nI gave you $%1$d\n", this.getMoney());
        this.setMoney(0);
    }

    public int getWaterMl() {
        return waterMl;
    }

    public void setWaterMl(int waterMl) {
        this.waterMl = Math.max(waterMl, 0);
    }

    public int getMilkMl() {
        return milkMl;
    }

    public void setMilkMl(int milkMl) { this.milkMl = Math.max(milkMl, 0); }

    public int getCoffeeGr() {
        return coffeeGr;
    }

    public void setCoffeeGr(int coffeeGr) {
        this.coffeeGr = Math.max(coffeeGr, 0);
    }

    public int getDisposableCups() {
        return disposableCups;
    }

    public void setDisposableCups(int disposableCups) {
        this.disposableCups = Math.max(disposableCups, 0);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = Math.max(money, 0);
    }

}

class CoffeeCup {
    protected String name;
    protected int waterMl;
    protected int milkMl;
    protected int coffeeGr;
    protected int price;

    public CoffeeCup() {
        this.name = "Coffee Cup";
        this.waterMl = 200;
        this.milkMl = 50;
        this.coffeeGr = 15;
        this.price = 5;
    }

    public String getName() {
        return name;
    }

    public int getWaterMl() {
        return waterMl;
    }

    public int getMilkMl() {
        return milkMl;
    }

    public int getCoffeeGr() {
        return coffeeGr;
    }

    public int getPrice() {
        return price;
    }
}

class Espresso extends CoffeeCup {
    public Espresso() {
        this.name = "Espresso";
        this.waterMl = 250;
        this.milkMl = 0;
        this.coffeeGr = 16;
        this.price = 4;
    }
}

class Latte extends CoffeeCup {
    public Latte() {
        this.name = "Latte";
        this.waterMl = 350;
        this.milkMl = 75;
        this.coffeeGr = 20;
        this.price = 7;
    }
}

class Cappuccino extends CoffeeCup {
    public Cappuccino() {
        this.name = "Cappuccino";
        this.waterMl = 200;
        this.milkMl = 100;
        this.coffeeGr = 12;
        this.price = 6;
    }
}


