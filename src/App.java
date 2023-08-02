import java.sql.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        //define database location
        //jdbc:database_type:path/to/database

        System.out.println();

        System.out.println("!!!Welcome to Sameer's and Ghanashyam's Yooeber Ride Database!!!");

        System.out.println();

        System.out.println("!---------------------------------!");

        String url = "jdbc:sqlite:src/yoober_project.db";
        Connection conn = null;
        String choice;

        try{
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        while(true)
        {

        System.out.println();

        System.out.println("Select a menu option: ");

        System.out.println();

        System.out.println("1. View all account details.");
        System.out.println("2. Calculate the average rating for a specific driver");
        System.out.println("3. Calculate the total money spent by a specific passenger");
        System.out.println("4. Create a new account");
        System.out.println("5. Submit a ride request");
        System.out.println("6. Complete a ride");

        System.out.println();

        System.out.print("Enter your option or type exit to exit the application:  ");

        //while(sc.hasNext()){}
        //sc.nextLine();

        //System.out.println("Type exit to exit the application: ");

        choice = sc.nextLine();

        System.out.println();


        switch(choice){
            
        case "1": 

            String accDetails1 = "SELECT a.FIRST_NAME, a.LAST_NAME, ad.STREET, ad.CITY, ad.PROVINCE, ad.POSTAL_CODE, a.PHONE_NUMBER, a.EMAIL, d.ID FROM drivers d INNER JOIN accounts a INNER JOIN addresses ad ON d.ID = a.ID AND a.ADDRESS_ID = ad.ID;";
            String accDetails2 = "SELECT a.FIRST_NAME, a.LAST_NAME, ad.STREET, ad.CITY, ad.PROVINCE, ad.POSTAL_CODE, a.PHONE_NUMBER, a.EMAIL, p.ID FROM passengers p INNER JOIN accounts a INNER JOIN addresses ad ON p.ID = a.ID AND a.ADDRESS_ID = ad.ID; ";
            try(
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(accDetails1);
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(accDetails2);){

                System.out.println();

                System.out.println("All account details:");

                System.out.println();

                System.out.println("First_Name" + "  Last_Name" + "    Street" + "                City" + "       Province" + "   Postal code" + "   Phone_Number" + "     Email" + "              ID");
                 
                System.out.println();

                System.out.println("Drivers: ");

                while(rs1.next()){
                    String fname = rs1.getString("FIRST_NAME");
                    String lname = rs1.getString("LAST_NAME");
                    String strt = rs1.getString("STREET");
                    String cty = rs1.getString("CITY");
                    String provn = rs1.getString("PROVINCE");
                    String post = rs1.getString("POSTAL_CODE");
                    String phnumber = rs1.getString("PHONE_NUMBER");
                    String email = rs1.getString("EMAIL");
                    int id = rs1.getInt("ID");

                    System.out.printf("%-11s %-12s %-21s %-10s %-10s %-13s %-16s %-17s %3d\n", fname, lname, strt, cty, provn, post, phnumber, email, id);
                }

                System.out.println("\nPassengers: ");

                while(rs2.next()){
                    String fname = rs2.getString("FIRST_NAME");
                    String lname = rs2.getString("LAST_NAME");
                    String street = rs2.getString("STREET");
                    String cty = rs2.getString("CITY");
                    String provn = rs2.getString("PROVINCE");
                    String post = rs2.getString("POSTAL_CODE");
                    String phnumber = rs2.getString("PHONE_NUMBER");
                    String email = rs2.getString("EMAIL");
                    int id = rs2.getInt("ID");

                    System.out.printf("%-11s %-12s %-21s %-10s %-10s %-13s %-16s %-17s %3d\n", fname, lname, street, cty, provn, post, phnumber, email, id);
                }
            }

            System.out.println();
            
        continue;

        case "2":
        
        System.out.println("Enter email address of the driver: ");

	    String d_email = sc.nextLine();

        Boolean chk1 = false;
	
	    while(!chk1){
	
	        try(PreparedStatement pstmt6 = conn.prepareStatement("SELECT drivers.ID FROM drivers INNER JOIN accounts ON drivers.ID = accounts.ID WHERE EMAIL = ?;");)
	        {
        	    pstmt6.setString(1, d_email);

                try(ResultSet rs6 = pstmt6.executeQuery();)
                {

        		    if(!rs6.next())
        		    {
            		    chk1 = false;

            		    System.out.println("Enter valid driver's email id: ");
            		    d_email = sc.nextLine();
        		    }

        		    else
        		    {
            		    do{
                		    rs6.getInt("ID");
            		    }while(rs6.next());    

            	    chk1 = true;
        		    }
        
    		    }
	        }
	  
        } 

        String avgrate = "SELECT DRIVER_ID, avg(RATING_FROM_PASSENGER) as Average_Rating FROM rides r INNER JOIN drivers d INNER JOIN accounts a on r.DRIVER_ID = d.ID AND d.ID = a.ID WHERE EMAIL = ? GROUP by DRIVER_ID;";

        	try(PreparedStatement pstmt = conn.prepareStatement(avgrate); )
        	{

                pstmt.setString(1, d_email);

                try(ResultSet rs3 = pstmt.executeQuery();)
                {
                	System.out.println("\nAverage rating for a specific driver: ");
                    System.out.println();
                    System.out.println("Driver_ID" + " Average_Rating");
                    System.out.println();
    
                    System.out.printf("%-9d %.1f\n",rs3.getInt("DRIVER_ID"), rs3.getDouble("Average_Rating"));
                            
                }   
                
            }
            catch(SQLException e){
                System.out.println("Driver does not have completed any rides yet!!: "+ e.getMessage());
            }
        System.out.println();

        //sc.nextLine();
        
        continue;

        case "3":

        System.out.println("Enter email address of the passenger: ");

        String p_email = sc.nextLine();

        Boolean chk2 = false;
	
	    while(!chk2){
	
	        try(PreparedStatement pstmt7 = conn.prepareStatement("SELECT passengers.ID FROM passengers INNER JOIN accounts ON passengers.ID = accounts.ID WHERE EMAIL = ?;");)
	        {
        	    pstmt7.setString(1, p_email);

                try(ResultSet rs7 = pstmt7.executeQuery();)
                {

        		    if(!rs7.next())
        		    {
            		    chk2 = false;

            		    System.out.println("Enter valid passenger's email id: ");
            		    p_email = sc.nextLine();
        		    }

        		    else
        		    {
            		    do{
                		    rs7.getInt("ID");
            		    }while(rs7.next());    

            	    chk2 = true;
        		    }
        
    		    }
	        }
	  
        } 

        String totalMoney = "SELECT ride_requests.PASSENGER_ID, sum(rides.CHARGE) as Total_Charge from rides INNER JOIN ride_requests INNER JOIN passengers INNER JOIN accounts on rides.REQUEST_ID = ride_requests.ID AND ride_requests.PASSENGER_ID = passengers.ID AND passengers.ID = accounts.ID WHERE EMAIL = ? GROUP by ride_requests.PASSENGER_ID;";

        try(
            PreparedStatement pstmt2 = conn.prepareStatement(totalMoney);
            //Scanner in1 = new Scanner(System.in); 
            ){

                //sc.nextLine(); 

                pstmt2.setString(1, p_email);

                System.out.println("\nTotal money spent by a specific passenger:");
                System.out.println();
                System.out.println("Passenger_ID" + " Total_Charge");
                System.out.println();
                try(ResultSet rs4 = pstmt2.executeQuery();)
                {
    
                System.out.printf("%-13d %.1f\n",rs4.getInt("PASSENGER_ID"), rs4.getDouble("Total_Charge"));

                }
        }
        catch(SQLException e){
            System.out.println("Passenger does not have completed any rides yet!!: "+ e.getMessage());
        }

        System.out.println();

        //sc.nextLine(); 

        continue;

        //4. Create a new account

        case "4":

        try(
            PreparedStatement instmt = conn.prepareStatement("INSERT INTO accounts(FIRST_NAME, LAST_NAME, BIRTHDATE, ADDRESS_ID, PHONE_NUMBER, EMAIL) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ){

            int driver_id = 0;
            //sc.nextLine();
            System.out.println("Enter first name: ");
            String fname = sc.nextLine();
            System.out.println("Enter last name: ");
            String lname = sc.nextLine();
            System.out.println("Enter birthday(Month Date, Year): ");
            String bday = sc.nextLine();
            System.out.println("Enter address ID: ");
            int address = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter phone number(eg. (123) 456-7890): ");
            String phone = sc.nextLine();
            System.out.println("Enter email address: ");
            String email = sc.nextLine();

            instmt.setString(1, fname);
            instmt.setString(2, lname);
            instmt.setString(3, bday);
            instmt.setInt(4, address);
            instmt.setString(5, phone);
            instmt.setString(6, email);

            instmt.executeUpdate();

            try(ResultSet key2 = instmt.getGeneratedKeys();)
            {
                driver_id = key2.getInt(1);
            }

            int ch;

            System.out.print("\nSpecify account will be used by: \n1. Passenger \n2. Driver \n3. Both \n\nOption= ");

            ch = sc.nextInt();

            switch(ch){

                case 1: 
                try(
                    PreparedStatement istmt1 = conn.prepareStatement("INSERT INTO passengers(CREDIT_CARD_NUMBER) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
                    ){
                        sc.nextLine();
                        
                        System.out.println("Enter credit card number(8-digit): ");
                        int ccno = sc.nextInt();
                        sc.nextLine();

                        istmt1.setInt(1, ccno);

                        istmt1.executeUpdate();

                        System.out.println("Account created successfully!!");
            }

            break;

            case 2: 
                try(
                    PreparedStatement istmt2 = conn.prepareStatement("INSERT INTO licenses(NUMBER, EXPIRY_DATE) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement istmt10 = conn.prepareStatement("INSERT INTO drivers(ID, LICENSE_ID) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
                    ){
                        
                        sc.nextLine();
                        System.out.println("Enter license number(10-digit): ");
                        String license = sc.nextLine();

                        System.out.println("Enter expiry date(Month Date, Year): ");
                        String expdate = sc.nextLine();


                        istmt2.setString(1, license);
                        istmt2.setString(2, expdate);

                        istmt2.executeUpdate();

                        try(
                            ResultSet key3 = istmt2.getGeneratedKeys();
                        ){
                            istmt10.setInt(1, driver_id);
                            istmt10.setInt(2, key3.getInt(1));

                            istmt10.executeUpdate();
                            
                        }

                        System.out.println("Account created successfully!!");
            }

            break;

            case 3:

            try(
                    PreparedStatement istmt3 = conn.prepareStatement("INSERT INTO passengers(CREDIT_CARD_NUMBER) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement istmt4 = conn.prepareStatement("INSERT INTO licenses(NUMBER, EXPIRY_DATE) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement istmt11 = conn.prepareStatement("INSERT INTO drivers(ID, LICENSE_ID) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)
                    ){
                        sc.nextLine();
                        
                        System.out.println("Enter credit card number(8-digit): ");
                        int ccno = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Enter license number(10-digit): ");
                        String license = sc.nextLine();
                        
                        System.out.println("Enter expiry date(Month Date, Year): ");
                        String expdate = sc.nextLine();

                        istmt3.setInt(1, ccno);

                        istmt4.setString(1, license);
                        istmt4.setString(2, expdate);

                        istmt3.executeUpdate();
                        istmt4.executeUpdate();

                        try(
                            ResultSet key3 = istmt4.getGeneratedKeys();
                        ){
                            istmt11.setInt(1, driver_id);
                            istmt11.setInt(2, key3.getInt(1));

                            istmt11.executeUpdate();
                        }

                        System.out.println("Account created successfully!!");
            }

            break;

        }

        }

        continue;

        //INSERT INTO ride_requests(PASSENGER_ID, PICKUP_LOCATION_ID, PICKUP_DATE, PICKUP_TIME, NUMBER_OF_RIDERS, DROPOFF_LOCATION_ID) VALUES(?, ?, ?, ?, ?, ?);

        case "5":

        int Passenger_id = 0;

        System.out.println("Enter passenger email ID: ");

        String p1_email = sc.nextLine();

        int l_ID;

        int l_ID1 = 0;

        Boolean chk = false;

        int favId = 0;

        int passenger_id = 0;

        while(!chk){

            String passID = "SELECT passengers.ID FROM passengers INNER JOIN accounts ON passengers.ID = accounts.ID WHERE EMAIL = ?;";
            
            try(
            PreparedStatement pstmt5 = conn.prepareStatement(passID);)
            {
            pstmt5.setString(1, p1_email);

            try(ResultSet rs5 = pstmt5.executeQuery();){

                if(!rs5.next()){
                    chk = false;
                    System.out.println("Passenger email record not valid!!");
                    System.out.println("Enter a valid passenger email ID: ");
                    p1_email = sc.nextLine();
                }

                else{
                    do{

                        Passenger_id = rs5.getInt("ID");
                    }while(rs5.next());

                    chk = true;
                }
            }
            }
        }

        try(
            PreparedStatement pstmt15 = conn.prepareStatement("SELECT passengers.ID FROM passengers INNER JOIN accounts on passengers.ID = accounts.ID WHERE accounts.EMAIL = ?;");
        ){

            pstmt15.setString(1, p1_email);

            try(
                ResultSet rs15 = pstmt15.executeQuery();
            )
            {
                passenger_id = rs15.getInt("ID");
            }

        }
        

        System.out.println("\nDo you want to choose your destination from the list of favourite destinations?(Enter: Yes/No) ");

        String op = sc.nextLine();

        if(op.equals("Yes") || op.equals("yes")){

            

            String favDes = "SELECT favourite_locations.LOCATION_ID, addresses.STREET, addresses.CITY, addresses.PROVINCE, addresses.POSTAL_CODE from favourite_locations INNER JOIN addresses INNER JOIN accounts on favourite_locations.LOCATION_ID = addresses.ID and addresses.ID = accounts.ADDRESS_ID WHERE favourite_locations.PASSENGER_ID = ?;";

            try(
                PreparedStatement stmt3 = conn.prepareStatement(favDes);
            )
                {

                stmt3.setInt(1, passenger_id);

                try(
                    ResultSet rs20 = stmt3.executeQuery();
                ){
                
                System.out.println("\nAll the favourite destinations:");

                System.out.println();

                System.out.println("LOCATION_ID" + "    STREET" + "                  CITY" + "          PROVINCE" + "       POSTAL_CODE");
                 
                System.out.println();

                while(rs20.next()){
                    l_ID = rs20.getInt("LOCATION_ID");
                    String street = rs20.getString("STREET");
                    String city = rs20.getString("CITY");
                    String province = rs20.getString("PROVINCE");
                    String postal = rs20.getString("POSTAL_CODE");

                    System.out.printf("%-15d%-24s%-14s%-15s%4s\n", l_ID, street,   city, province, postal);
                }
                }

                System.out.println("Enter the ID corresponding to your choice of destination from the list of favourites:");

                favId = sc.nextInt();

                sc.nextLine();

                System.out.println("Enter pick-up location id: ");

                int p_loc = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter desired pick-up date(Month Date, Year): ");
                String p_date = sc.nextLine();

                System.out.println("Enter desired pick-up time(eg. 12:00 AM/PM): ");
                String p_time = sc.nextLine();

                System.out.println("Enter total number of riders: ");

                int numberRiders = sc.nextInt();
                sc.nextLine();
            try(
                PreparedStatement pstmt10 = conn.prepareStatement("INSERT INTO ride_requests(PASSENGER_ID, PICKUP_LOCATION_ID, PICKUP_DATE, PICKUP_TIME, NUMBER_OF_RIDERS, DROPOFF_LOCATION_ID) VALUES(?, ?, ?, ?, ?, ?);")
            ){
    
                pstmt10.setInt(1,Passenger_id);
                pstmt10.setInt(2, p_loc);
                pstmt10.setString(3, p_date);
                pstmt10.setString(4, p_time);
                pstmt10.setInt(5, numberRiders);
                pstmt10.setInt(6, favId);
    
                pstmt10.executeUpdate();

                System.out.println("PASSENGER_ID" + " PICKUP_LOCATION_ID" + " PICKUP_DATE" + " PICKUP_TIME" + "    NUMBER_OF_RIDERS" + " DROPOFF_LOCATION_ID");
                System.out.printf("%-12d %-18d %-11s %-11s %-16d %d\n", Passenger_id, p_loc, p_date, p_time, numberRiders, favId);
                System.out.println("\nRide request submitted!!");
            }

            }

            
        }

        else if(op.equals("No") || op.equals("no")){

           try(
            PreparedStatement instmt1 = conn.prepareStatement("INSERT INTO addresses(STREET, CITY, PROVINCE, POSTAL_CODE) VALUES(?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);)
            {
            
            System.out.println("Enter your address: ");
            System.out.println("Enter street: ");
            String street = sc.nextLine();
            System.out.println("Enter city: ");
            String city = sc.nextLine();
            System.out.println("Enter province: ");
            String province = sc.nextLine();
            System.out.println("Enter postal code: ");
            String post = sc.nextLine();
            

            instmt1.setString(1, street);
            instmt1.setString(2, city);
            instmt1.setString(3, province);
            instmt1.setString(4, post);

            instmt1.executeUpdate();

            ResultSet key1 = instmt1.getGeneratedKeys();
            if(key1.next()){

                l_ID1 = key1.getInt(1);
                System.out.println("Location ID generated: " + key1.getInt(1));
            }
            
            }

        
        System.out.println("Do you want to make this destination a new favourite?(Enter: Yes/No)");

        String op1 = sc.nextLine();

        if(op1.equals("yes") || op1.equals("Yes")){

            try(
            PreparedStatement instmt2 = conn.prepareStatement("INSERT INTO favourite_locations(PASSENGER_ID, LOCATION_ID, NAME) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);)
            {
           
            System.out.println("Enter Destination_Name: ");
            String destName = sc.nextLine();
            

            instmt2.setInt(1, Passenger_id);
            instmt2.setInt(2, l_ID1);
            instmt2.setString(3, destName);
            

            instmt2.executeUpdate();
            
            }

        }

        else if(op1.equals("no") || op1.equals("No")){

            System.out.println("Enter pick-up location id: ");

        int p_loc = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter desired pick-up date(Month Date, Year): ");
        String p_date = sc.nextLine();

        System.out.println("Enter desired pick-up time(eg. 12:00 AM/PM): ");
        String p_time = sc.nextLine();

        System.out.println("Enter total number of riders: ");

        int numberRiders = sc.nextInt();
        sc.nextLine();
            try(
                PreparedStatement pstmt10 = conn.prepareStatement("INSERT INTO ride_requests(PASSENGER_ID, PICKUP_LOCATION_ID, PICKUP_DATE, PICKUP_TIME, NUMBER_OF_RIDERS, DROPOFF_LOCATION_ID) VALUES(?, ?, ?, ?, ?, ?);")
            ){
    
                pstmt10.setInt(1,Passenger_id);
                pstmt10.setInt(2, p_loc);
                pstmt10.setString(3, p_date);
                pstmt10.setString(4, p_time);
                pstmt10.setInt(5, numberRiders);
                pstmt10.setInt(6, l_ID1);
               
    
                pstmt10.executeUpdate();

                System.out.println("PASSENGER_ID" + " PICKUP_LOCATION_ID" + " PICKUP_DATE" + " PICKUP_TIME" + "    NUMBER_OF_RIDERS" + " DROPOFF_LOCATION_ID");
                System.out.printf("%-12d %-18d %-11s %-11s %-16d %d\n", Passenger_id, p_loc, p_date, p_time, numberRiders, l_ID1);
                System.out.println("\nRide request submitted!!");
            }
        }

        else{
            System.out.println("Select valid option (Yes/No)!!");
            break;
        }
        

        }

        else 
        System.out.println("Select valid option (Yes/No)!!");

        continue;

        case "6":

        //sc.nextLine();

        System.out.println("Details of all uncompleted rides:");

        try(
            Statement stmt6 = conn.createStatement();
            ResultSet rs11 = stmt6.executeQuery("SELECT ride_requests.ID as ID, accounts.FIRST_NAME as First_Name, accounts.LAST_NAME as Last_Name, addresses.STREET as Street, addresses.CITY as City, ride_requests.PICKUP_DATE as Pickup_Date, ride_requests.PICKUP_TIME as Pickup_Time FROM accounts INNER JOIN passengers INNER JOIN ride_requests INNER JOIN addresses on accounts.ID = passengers.ID AND passengers.ID = ride_requests.PASSENGER_ID AND ride_requests.PICKUP_LOCATION_ID = addresses.ID WHERE ride_requests.PASSENGER_ID = passengers.ID;");)
        {
            System.out.println("ID" + "      First_Name" + "      Last_Name" + "    Street" + "                  City" + "         Pickup_Date" + "        Pickup_Time");
            while(rs11.next())
            {
                System.out.printf("%-7d %-15s %-12s %-23s %-12s %-18s %s\n", rs11.getInt("ID"), rs11.getString("First_Name"), rs11.getString("Last_Name"), rs11.getString("Street"), rs11.getString("City"), rs11.getString("Pickup_Date"), rs11.getString("Pickup_Time"));
            }
            
        }

        System.out.println("Enter the ID corresponding to the ride you want to complete: ");

        int ride_id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter email address of the driver: ");

	    String dr_email = sc.nextLine();

        int dr_id = 0;

        String a_date, a_time;

        Boolean chk3 = false;
	
	    while(!chk3)
        {
	
	        try(PreparedStatement pstmt12 = conn.prepareStatement("SELECT drivers.ID FROM drivers INNER JOIN accounts ON drivers.ID = accounts.ID WHERE EMAIL = ?;");)
	        {
        	    pstmt12.setString(1, dr_email);

                try(ResultSet rs12 = pstmt12.executeQuery();)
                {

        		    if(!rs12.next())
        		    {
            		    chk3 = false;

            		    System.out.println("Enter valid driver's email id: ");
            		    dr_email = sc.nextLine();
        		    }

        		    else
        		    {
            		    do{
                		    rs12.getInt("ID");
            		    }while(rs12.next());    

            	    chk3 = true;
        		    }
        
    		    }
	        }
	  
        }
        
        try(
            PreparedStatement pstmt15 = conn.prepareStatement("SELECT drivers.ID FROM drivers INNER JOIN accounts on drivers.ID = accounts.ID WHERE accounts.EMAIL = ?;");
        ){

            pstmt15.setString(1, dr_email);

            try(
                ResultSet rs15 = pstmt15.executeQuery();
            )
            {
                dr_id = rs15.getInt("ID");
            }

        }

        try(
            PreparedStatement pstmt17 = conn.prepareStatement("SELECT PICKUP_DATE, PICKUP_TIME FROM ride_requests WHERE ID = ?;")
        ){

            pstmt17.setInt(1, ride_id);

            try(ResultSet rs17 = pstmt17.executeQuery();){

                a_date = rs17.getString("PICKUP_DATE");
                a_time = rs17.getString("PICKUP_TIME");
            }
        }

        try(
            PreparedStatement pstmt16 = conn.prepareStatement("INSERT INTO rides(DRIVER_ID, REQUEST_ID, ACTUAL_START_DATE, ACTUAL_START_TIME, ACTUAL_END_DATE, ACTUAL_END_TIME, RATING_FROM_DRIVER, RATING_FROM_PASSENGER, DISTANCE, CHARGE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")
        ){
            
            pstmt16.setInt(1, dr_id);
            pstmt16.setInt(2, ride_id);
            pstmt16.setString(3, a_date);
            pstmt16.setString(4, a_time);

            System.out.println("Enter actual end date(Month Date, Year): ");
            String e_date = sc.nextLine();

            System.out.println("Enter actual end time(eg. 12:00 AM/PM): ");
            String e_time = sc.nextLine();

            System.out.println("Enter driver's rating: ");
            int d_rate = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter passenger's rating: ");
            int p_rate = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter distance travelled: ");
            double dist = sc.nextDouble();

            System.out.println("Enter cost of the ride: ");
            double cost = sc.nextDouble();

            sc.nextLine();

            pstmt16.setString(5, e_date);
            pstmt16.setString(6, e_time);
            pstmt16.setInt(7, d_rate);
            pstmt16.setInt(8, p_rate);
            pstmt16.setDouble(9, dist);
            pstmt16.setDouble(10, cost);


            pstmt16.executeUpdate();
            System.out.println("Ride completed!!");
        }

        continue;

        case "exit":

        System.exit(0);

        sc.close();

        if(conn != null){
            conn.close();
        }

        break;

        default: 
        
        System.out.println("Enter a valid choice!!");

        continue;

    }
        
    }

}

}