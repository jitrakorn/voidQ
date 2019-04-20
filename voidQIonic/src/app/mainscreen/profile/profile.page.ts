import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { SessionService } from '../../session.service';
import { PatientService } from '../../patient.service';
import { Patient } from '../../patient';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  updateError: boolean;
 updateErrorMessage: string;
  currentPatient: Patient;
  submitted: boolean;
  

  constructor(private navigationCtrl: NavController,
    private patientService: PatientService,
    public sessionService: SessionService) { 
      this.submitted = false;
    }

  ngOnInit() {
    this.currentPatient = this.sessionService.getCurrentPatient();

  }
  goToLogin() {
    this.navigationCtrl.navigateRoot('/home');

  }
  update(updatePatientForm: NgForm) {
    this.submitted = true;
    if (updatePatientForm.valid) {
      this.patientService.updatePatientDetails(this.currentPatient.email, this.currentPatient.password, this.currentPatient).subscribe (
        response => {
          console.log(response);
          alert('Patient details sucessfully updated!');
        },
        error => {
     this.updateError = true;
          this.updateErrorMessage = error
          console.log("ERROR");
        }
      )
    } else {
      
    }
  }
}