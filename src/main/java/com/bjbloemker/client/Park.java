package com.bjbloemker.client;

public class Park {

    private String name;
    private double admissionFee;
    private int visitorCount;

    public Park(String name, double admissonFee){
        setName(name);
        setAdmissionFee(admissonFee);
        visitorCount = 0;
    }






    public void setAdmissionFee(double admissionFee){
        if(validAdmissonFee(admissionFee)){
            this.admissionFee = admissionFee;
        }
    }

    private boolean validAdmissonFee(double feeToAssess){
        if(feeToAssess < 0){
            throw new IllegalArgumentException("Fee cannot be less than zero!");
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public double getAdmissionFee() {
        return admissionFee;
    }

    private void incrementVisitorCount(){
        visitorCount++;
    }

    private void setName(String name){
        this.name = name;
    }
}
