import { Component, ViewChild } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { SessionService } from './session.service';
import { Environment } from '@ionic-native/google-maps/ngx';


@Component({
	selector: 'app-root',
	templateUrl: 'app.component.html'
})

export class AppComponent {




	constructor(private platform: Platform,
		private splashScreen: SplashScreen,
		private statusBar: StatusBar,
		public sessionService: SessionService, ) {
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

			Environment.setEnv({
				// api key for server
				'API_KEY_FOR_BROWSER_RELEASE': 'AIzaSyBzvYLqwGw52vbHGWqZw2sCOtzAoZ4fxc0',

				// api key for local development
				'API_KEY_FOR_BROWSER_DEBUG': 'AIzaSyBzvYLqwGw52vbHGWqZw2sCOtzAoZ4fxc0'
			});

			this.statusBar.styleDefault();
			this.splashScreen.hide();
		});
	}
}
