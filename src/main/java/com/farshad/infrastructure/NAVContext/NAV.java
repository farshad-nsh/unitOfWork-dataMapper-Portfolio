package com.farshad.infrastructure.NAVContext;

public class NAV {


   private static int numberOfShares;
   private static double nav;
   private Statement statement;

   public void NAV(){
      System.out.println("inside constructor for NAV");
   }

   public  void setInitialNAV() {
      nav = 0;
      numberOfShares=3;
   }

   public void update(Statement statement){
      this.statement=statement;
      if (statement.getType().equals("asset")){
          nav=NAV.getNAV()+(statement.getValue())/numberOfShares;
         System.out.println("new asset");
      }else if (statement.getType().equals("liability")){
          nav=NAV.getNAV()-(statement.getValue())/numberOfShares;
         System.out.println("new liability");
      }else{
         throw new Error("unkown statement in farshad's software!");
      }
   }

   public static double getNAV(){
      return nav;
   }
}