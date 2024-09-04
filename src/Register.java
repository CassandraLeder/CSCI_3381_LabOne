/*
    LabOne purse and GUI application for CSCI3381
    Cassandra Leder 28/8/24
 */

import java.util.*;

enum Form {
    BILL,
    COIN
}

record Denomination(String name, double amt, Form form, String img) {}

class Purse {
    Map<Denomination, Double> cash = new HashMap<>();

    // default constructor

    public void add(Denomination moneytype, int num) {
        this.cash.put(moneytype, (double) num);
    }

    public double remove(Denomination moneytype, int num) {
        if ((moneytype.amt() * num) > this.cash.get(moneytype)) {
            throw new ArithmeticException("Subtraction from purse greater than money in purse");
        }
        else {
            this.cash.put(moneytype, (double) this.cash.get(moneytype) - num);
        }

        return (moneytype.amt() * num);
    }

    public double getValue() {
        double sum = 0;

        for (Denomination denomination : this.cash.keySet()) {
            sum += (denomination.amt() * this.cash.get(denomination)); // ex. quarter * 4 or $1.00
        }

        return(sum);
    }

    @Override public String toString() {
        String string = "";

        for (Map.Entry<Denomination, Double> entry : cash.entrySet())
        {
            string += entry.getValue().intValue() + " " + entry.getKey().name() + " ";
        }

        return(string);
    }
}

public class Register {
    private final List<Denomination> denomination = new ArrayList<>();

    // constructor
    Register()
    {
        // write high to low to avoid wasteful use of algorithms
        denomination.add(new Denomination("one hundred dollar bill", 100.0, Form.BILL, "currency/one_hundred_note.png"));
        denomination.add(new Denomination("fifty dollar bill", 50.00, Form.BILL, "currency/fifty_note.png"));
        denomination.add(new Denomination("twenty dollar bill", 20.00, Form.BILL, "currency/twenty_note.png"));
        denomination.add(new Denomination("ten dollar bill", 10.00, Form.BILL, "currency/ten_note.png"));
        denomination.add(new Denomination("five dollar bill", 5.00, Form.BILL, "currency/five_note.png"));
        denomination.add(new Denomination("one dollar bill", 1.00, Form.BILL, "currency/one_note.png"));
        denomination.add(new Denomination("quarter", 0.25, Form.COIN, "currency/quarter.png"));
        denomination.add(new Denomination("dime", .10, Form.COIN, "currency/dime.png"));
        denomination.add(new Denomination("nickel", .05, Form.COIN,"currency/nickel.png"));
        denomination.add(new Denomination("penny", .01, Form.COIN, "currency/penny.png"));
    }


    public Purse makeChange(double amt) {
        Purse purse = new Purse();
        int count = 0;

        if (amt <= 0) return purse;

        // this method only works if denomination is sorted high to low

        for (Denomination denominations : denomination) {
            if (amt > 0) {
                count = (int) (amt / denominations.amt()); // if denomination has one or more of the currency type, add to purse

                if (count > 0) {
                    purse.add(denominations, count);
                    amt -= denominations.amt() * count;
                }
            }
        }

        return purse;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the amount of money to start out with : ");

        Register register = new Register();
        Purse myPurse = register.makeChange(sc.nextDouble());

        if (myPurse.getValue() == 0) {
            throw new Error("Empty purse");
        }
        else {
            System.out.println("Purse contains $" + myPurse.getValue());
            System.out.println(myPurse.toString());
        }
    }
}