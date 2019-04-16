import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { PatientService } from '../patient.service';
import { Patient } from '../patient';
import { NavController } from '@ionic/angular';




@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})

export class LoginPage implements OnInit 
{
	submitted: boolean;
	username: string;
	password: string;
	loginError: boolean;
	errorMessage: string;
	
	
	
	constructor(private router: Router,
				public sessionService: SessionService,
				private patientService: PatientService,
				private navigationCtrl: NavController)
	{
		this.submitted = false;
	}

	
	
	ngOnInit()
	{
	}
	
	
	
	clear()
	{
		this.username = "";
		this.password = "";
	}
	
	
	
	patientLogin(patientLoginForm: NgForm)
	{
		this.submitted = true;
		
		if (patientLoginForm.valid)
		{
			this.sessionService.setUsername(this.username);
			this.sessionService.setPassword(this.password);
						
			this.patientService.patientLogin(this.username, this.password).subscribe(
				response => {										
					let patient: Patient = response.patientEntity;
					

					
					if(patient != null)
					{
						this.sessionService.setIsLogin(true);
						this.sessionService.setCurrentPatient(patient);
						this.loginError = false;					
						
						this.navigationCtrl.navigateForward("/home");										
					}
					else
					{
						this.loginError = true;
					}
				},
				error => {
					this.loginError = true;
					this.errorMessage = error
				}
			);
		}
		else
		{
		}
	}
	
	
	
	patientLogout(): void
	{
		this.sessionService.setIsLogin(false);
		this.sessionService.setCurrentPatient(null);
		
		this.navigationCtrl.navigateForward("/home");	
	}
}
