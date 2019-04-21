import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { PatientService } from '../patient.service';
import { Patient } from '../patient';

import { NavController } from '@ionic/angular';



@Component({
	selector: 'app-home',
	templateUrl: 'home.page.html',
	styleUrls: ['home.page.scss'],
})

export class HomePage implements OnInit {

	submitted: boolean;
	username: string;
	password: string;
	loginError: boolean;
	loginErrorMessage: string;

	constructor(private router: Router,
		public sessionService: SessionService,
		private patientService: PatientService,
		private navigationCtrl: NavController) {
		this.submitted = false;
	}

	

	ngOnInit() {
	
		if(this.sessionService.getIsLogin())
		{
			this.navigationCtrl.navigateForward('/tabs');
		}
		
	}

	patientLogin(patientLoginForm: NgForm) {
		this.submitted = true;
		if (patientLoginForm.valid) {
			this.sessionService.setEmail(this.username);
			this.sessionService.setPassword(this.password);

			this.patientService.patientLogin(this.username, this.password).subscribe(
				response => {
					let patient: Patient = response.patientEntity;

					if (patient != null) {
						this.sessionService.setIsLogin(true);
						this.sessionService.setCurrentPatient(patient);
						this.loginError = false;
						this.navigationCtrl.navigateForward('/tabs');
					}
					else {
						this.loginError = true;
					}
				},
				error => {
					this.loginError = true;
					this.loginErrorMessage = error;
				}
			);
		} else {

		}
	}

	goToRegisterPage() {
		this.navigationCtrl.navigateRoot('/register');
	}
	goToResetPasswordPage() {
		this.navigationCtrl.navigateRoot('/resetpassword');
	}
}
