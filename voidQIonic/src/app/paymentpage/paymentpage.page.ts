import { Component, OnInit } from '@angular/core';
import { PayPal, PayPalPayment, PayPalConfiguration } from '@ionic-native/paypal/ngx';
@Component({
  selector: 'app-paymentpage',
  templateUrl: './paymentpage.page.html',
  styleUrls: ['./paymentpage.page.scss'],
})
export class PaymentpagePage implements OnInit {

  constructor(private payPal: PayPal) { }

  ngOnInit() {
  }

pay()
{

  this.payPal.init({
    PayPalEnvironmentProduction: 'ASbfnf2jyx2vJzLGRfvXDjy9l-DYbFEC-ZoGDSRxC3ay5klM8ieP0cYAUp0_nvurifNktv5A99DBomNK',
    PayPalEnvironmentSandbox: 'ASbfnf2jyx2vJzLGRfvXDjy9l-DYbFEC-ZoGDSRxC3ay5klM8ieP0cYAUp0_nvurifNktv5A99DBomNK'
  }).then(() => {
    
    // Environments: PayPalEnvironmentNoNetwork, PayPalEnvironmentSandbox, PayPalEnvironmentProduction
    this.payPal.prepareToRender('PayPalEnvironmentSandbox', new PayPalConfiguration({
      // Only needed if you get an "Internal Service Error" after PayPal login!
      //payPalShippingAddressOption: 2 // PayPalShippingAddressOptionPayPal
    })).then(() => {
      let payment = new PayPalPayment('3.33', 'USD', 'Description', 'sale');
      this.payPal.renderSinglePaymentUI(payment).then(() => {
        // Successfully paid
  
        // Example sandbox response
        //
        // {
        //   "client": {
        //     "environment": "sandbox",
        //     "product_name": "PayPal iOS SDK",
        //     "paypal_sdk_version": "2.16.0",
        //     "platform": "iOS"
        //   },
        //   "response_type": "payment",
        //   "response": {
        //     "id": "PAY-1AB23456CD789012EF34GHIJ",
        //     "state": "approved",
        //     "create_time": "2016-10-03T13:33:33Z",
        //     "intent": "sale"
        //   }
        // }
      }, (error) => {
        console.log(error);
        // Error or render dialog closed without being successful
      });
    }, (error) => {
      console.log(error);
      // Error in configuration
    });
  }, (error) => {
    console.log(error);
    // Error in initialization, maybe PayPal isn't supported or something else
  });
}

}
