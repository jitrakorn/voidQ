import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import { NavController } from '@ionic/angular';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  constructor(
    private navigationCtrl: NavController,
    private patientService: PatientService,
    public sessionService: SessionService
  ) { }

  ngOnInit() {
    var currentPatient = this.sessionService.getCurrentPatient();
    console.log("HIII" + currentPatient);
    console.log("o is " + currentPatient[0]);
    console.log(currentPatient.email);
  }

  goToLogin() {
    this.navigationCtrl.navigateRoot('/home');

  }

}
