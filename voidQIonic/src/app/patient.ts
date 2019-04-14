export class Patient {

    patientId: number;
    firstName: string;
    lastName: string;
    username: string;
    password: string;

    constructor(patientId?: number, firstName?: string, lastName?: string, username?: string, password?: string)
    {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
