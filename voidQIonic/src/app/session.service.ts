import { Injectable } from '@angular/core';

import { Patient } from './patient';




@Injectable({
  providedIn: 'root'
})

export class SessionService 
{
	constructor()
	{		
	}



	getIsLogin(): boolean
	{
		if(sessionStorage.isLogin == "true")
		{
			return true;
		}
		else
		{
			return false;
		}
	}



	setIsLogin(isLogin: boolean): void
	{
		sessionStorage.isLogin = isLogin;
	}



	getCurrentPatient(): Patient
	{
		return JSON.parse(sessionStorage.currentPatient);
	}



	setCurrentPatient(currentPatient: Patient): void
	{		 
		sessionStorage.currentPatient = JSON.stringify(currentPatient);
	}
	
	
	
	getEmail(): string
	{
		return sessionStorage.email;
	}

	setEmail(email: string): void
	{
		sessionStorage.email = email;
	}
	
	
	
	getPassword(): string
	{
		return sessionStorage.password;
	}



	setPassword(password: string): void
	{
		sessionStorage.password = password;
	}
	
	
	



	
	
	

}
