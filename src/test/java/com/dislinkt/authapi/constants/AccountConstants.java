package com.dislinkt.authapi.constants;

import java.time.LocalDateTime;
import java.time.Month;

import com.dislinkt.authapi.domain.account.Gender;


public class AccountConstants {

	
	public static final String NEW_ACCOUNT_EMAIL = "newaccount@gmail.com";
	public static final String NEW_ACCOUNT_NAME = "New Account";
	public static final String NEW_ACCOUNT_USERNAME = "newaccount";
	public static final String NEW_ACCOUNT_PHONE = "00000000";
	public static final String NEW_ACCOUNT_UUID = "6c20fb12-50d8-4322-ba33-9c05203868e9";
	public static final String NEW_ACCOUNT_PASSWORD = "asdf";
	public static final LocalDateTime NEW_ACCOUNT_DATE_OF_BIRTH = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
	public static final Gender NEW_ACCOUNT_GENDER = Gender.MALE;
	public static final boolean NEW_ACCOUNT_IS_PUBLIC = true;
	
	public static final String DB_ACCOUNT_USERNAME = "user1";
	public static final String DB_ACCOUNT_PASSWORD = "$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO"; //asdf
	public static final String DB_ACCOUNT_EMAIL = "user1@mail.com";
	public static final String DB_ACCOUNT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhd"
												+"CI6MTY2MzAwMjgwMCwiZXhwIjoxNjYzMDA2NDAwfQ.pi-"
												+"obvwDiP6vLtWspcoOT7d_hhEnsHEhGedR79JhuSg";
	
	public static final String DB_UNKNOWN_EMAIL = "blabla";
	public static final String DB_UNKNOWN_USERNAME = "blabla";

}
