# TechnicalTest


The problem

Sample data represents the instructions sent by various clients to JP Morgan to execute in the international market.

  A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where the work week starts Sunday and ends     Thursday. No other holidays to be taken into account.
  A trade can only be settled on a working day.
  If an instructed settlement date falls on a weekend, then the settlement date should be changed to
  the next working day.
  USD amount of a trade = Price per unit * Units * Agreed Fx
  
Requirements

Create a report that shows 
  Amount in USD settled incoming everyday
  Amount in USD settled outgoing everyday
  Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest
  amount for a buy instruction, then foo is rank 1 for outgoing
  
Solution
  
  Application is creted using java 1.8 in Eclipse IDE as maven project.
  
  The Main Class to start this application is SuperStockAPP.Java
   
  1) I am using trade.txt file as my input file and delimiter as /.
  2) Then TextFileProcessor class will parse the text file and load the trade bean and return List of trade bean object.
  3) While populating TradeBean object incrementDaysExcludingWeekends method in WeekendDateConverterImpl.java is called to 
     check the weekend logic based on the currency and if settlement date falls on the weekend in changed accordinlgy 
     to the firstweekday date.Total Amount of each trade is converted in to the USD using calcuateTotalTradeAmt method in
     TextFileProcessor.java class.
  4) Once TradeBean List is there it passed to generateReport method in GenerateReportHelper.Java where first we loop through the list
     and populate Map based on buy,sell and clacluate the toatlTradeAmount for each settlement date and entity 
     seperatley and put in map and set in the report bean.
  5) Once ReportBean is got it passed to displayReport method in GenerateReportHelper.Java where its looped through each map 
     in the report bean and displayed to the user on the console.
  6) Junit for each testable methods in TextFileProcessor.java,WeekendDateConverterImpl.java and  GenerateReportHelper.java is been
     added and tested.
     
    
     Sample OutPut
     
     ******Everyday Total Settlement Amount for Outgoing(Buy) Trades based on settlement date********
      Settlement Date: 06 Apr 2017, TotalAMount: 10025.0 USD
      Settlement Date: 07 Apr 2017, TotalAMount: 20100.0 USD
      Settlement Date: 09 Apr 2017, TotalAMount: 70400.0 USD
      Settlement Date: 10 Apr 2017, TotalAMount: 80450.0 USD
     ******Everyday Settlement Amount for Incoming(Sell) Trades based on settlement date********
      Settlement Date: 06 Apr 2017, TotalAMount: 5000.0 USD
      Settlement Date: 09 Apr 2017, TotalAMount: 42000.0 USD
      Settlement Date: 10 Apr 2017, TotalAMount: 34500.0 USD
     ******EveryDay Total Settlement Amount for Buy(Outgoing) Trades Based On Entity In Descending Order********
      Entity Name: EI, TotalAMount: 70400.0 USD
      Entity Name: IN, TotalAMount: 70400.0 USD
      Entity Name: CI, TotalAMount: 20100.0 USD
      Entity Name: JP, TotalAMount: 10050.0 USD
      Entity Name: MS, TotalAMount: 10025.0 USD
     ******EveryDay Total Settlement Amount for Sell(Incoming) Trades Based On Entity In Descending Order********
      Entity Name: EI, TotalAMount: 27000.0 USD
      Entity Name: IN, TotalAMount: 27000.0 USD
      Entity Name: FI, TotalAMount: 15000.0 USD
      Entity Name: JP, TotalAMount: 12500.0 USD
        
  
