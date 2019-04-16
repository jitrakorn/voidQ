export class Patient {

    patientId: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    email: string;
    password: string;

    constructor(patientId?: number, firstName?: string, lastName?: string, phoneNumber?: string, email?: string, password?: string)
    {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }
}
