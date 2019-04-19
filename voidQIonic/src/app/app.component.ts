import { Component, ViewChild } from '@angular/core';

import { Platform, NavController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { SessionService } from './session.service';


@Component({
	selector: 'app-root',
	templateUrl: 'app.component.html'
})

export class AppComponent {




	constructor(private platform: Platform,
		private splashScreen: SplashScreen,
		private statusBar: StatusBar,
		public sessionService: SessionService,private navigationCtrl: NavController ) {
		this.initializeApp();


		

		// if (this.sessionService.getIsLogin()) {
		// 	this.appPages = [
		// 		{
		// 			title: 'Home',
		// 			url: '/home',
		// 			icon: 'home'
		// 		},
		// 		{
		// 			title: 'Logout',
		// 			url: '/login',
		// 			icon: 'exit'
		// 		}
		// 	];
		// }
		// else {
		// 	this.appPages = [
		// 		{
		// 			title: 'Home',
		// 			url: '/home',
		// 			icon: 'home'
		// 		},
		// 		{
		// 			title: 'Login',
		// 			url: '/login',
		// 			icon: 'lock'
		// 		},
		// 		{
		// 			title: 'New menu',
		// 			url: '/tabs',
		// 			icon: 'lock'
		// 		},
		// 		{
		// 			title: 'Payment Page',
		// 			url: '/paymentpage',
		// 			icon: 'lock'
		// 		}
		// 	];
		// }
	}




	initializeApp() {
		this.platform.ready().then(() => {

			this.statusBar.styleDefault();
			this.splashScreen.hide();
		});
	}
}
