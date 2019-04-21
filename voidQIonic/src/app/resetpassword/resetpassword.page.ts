import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { PatientService } from '../patient.service';
import { NavController } from '@ionic/angular';
@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.page.html',
  styleUrls: ['./resetpassword.page.scss'],
})
export class ResetpasswordPage implements OnInit {
	username: string;
  constructor(private patientService: PatientService,private navigationCtrl: NavController,private location: Location) { }

  ngOnInit() {
  }
  goToHome() {
	this.location.back();
}
  patientResetPassword(patientResetForm: NgForm) {
		
		if (patientResetForm.valid) {
		

			this.patientService.resetPassword(this.username).subscribe(
				response => {
				console.log(response);
				alert(response.message);
				},
				error => {
				
				}
			);
		} else {

		}
	
}
}