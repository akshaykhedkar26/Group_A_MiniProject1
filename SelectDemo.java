package com.quiz1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SelectDemo {

	public static void main(String[] args) {
		int correctAns = 0;
		int wrongAns = 0;

		try {
//step1: loading driver class.
			Class.forName("com.mysql.jdbc.Driver");

//step2: establishing connection.
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject", "root", "root");

			/*
			 * 1st Requirement of project: Store the 10 multiple choice questions related to
			 * java into database with correct answer. (stored in sql already just
			 * retrieving it through select query to perform some action on it)
			 */

//step3: creating prepared statement.
			PreparedStatement ps = con.prepareStatement("select * from MCQ ");
			

//step4: executing query for some give fields

			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Scanner sc = new Scanner(System.in);

				// 3rd requirement of project: One question has four options only.

				System.out.println("Questionnumber >> " + rs.getInt(1));
				System.out.println("Questions >> " + rs.getString(2));
				System.out.println("Option_A >> " + rs.getString(3));
				System.out.println("Option_B >> " + rs.getString(4));
				System.out.println("Option_C >> " + rs.getString(5));
				System.out.println("Option_D >> " + rs.getString(6));
				System.out.println();

				// 4th requirement of project:User will choose the answers.
				System.out.println("enter the Answer");
				String Ans1 = sc.next();

				/* it will ignore the case of answer which has submitted by students */
				if (Ans1.equalsIgnoreCase(rs.getString(7))) {

					System.out.println("Answer is correct");
					System.out.println();

					correctAns = correctAns + 1;
				} else {

					System.out.println("Answer is Wrong");
					System.out.println();

					wrongAns = wrongAns + 1;
				}
			}
			

			System.out.println("---------------------------Result--------------------------------");

			Scanner sc = new Scanner(System.in);
			

			// 5th requirement of project: 5.Also ability to store the multiple student
			// score into database.

			// 8th Requirement of project: Display the list of student’s id, name with score
			// into console as sorting order.
			
			
			PreparedStatement prs = con.prepareStatement("insert into StudentRecords(Name,Score)values(?,?)");

			System.out.println("Enter Full Name of Student");

			String name = sc.next();
			prs.setString(1, name);

			prs.setInt(2, correctAns);
			int i = prs.executeUpdate();

			System.out.println("Score= " + correctAns);

			// 6th Requirement of project: System will display the result- it means what is
			// the score out of 10.
			System.out.println("total Scored achieved by Student out of 10 is: " + correctAns);

			// 7th Project Requirement: Also display the marks as below Class A- 8-10 Class
			// B- 6-8 Class C- 5 Class D- <5 then its fail. */

			if (correctAns >= 8 && correctAns <= 10) {
				System.out.println("A Class");
			} else if (correctAns >= 6 && correctAns <= 8) {
				System.out.println("B Class");
			} else if (correctAns >= 5 && correctAns < 6) {
				System.out.println("C Class");
			} else if (correctAns < 5 && correctAns >= 1) {
				System.out.println("D Class");
			}

			System.out.println("Record is inserted successfully.." + i);

			con.close();
			ps.close();
			rs.close();

			// 9th Requirement of Project:To get the particular record by using student id
			// only. If someone wants to retrieve theirs score from database.

			/*
			 * If we want to retrieve the score from database we will use the select query
			 * directly in mysql workbench i.e : /* select * from studentRecords where id=..
			 */
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
