import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {UserService} from '../../service/user.service';
import {User} from '../../models/User';
import {NotificationService} from '../../service/notification.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  public profileEditForm: FormGroup;

  constructor(private dialogRef: MatDialogRef<EditUserComponent>,
              private fb: FormBuilder,
              private notificationService: NotificationService,
              @Inject(MAT_DIALOG_DATA) public data,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.profileEditForm = this.createProfileEditForm();
  }

  createProfileEditForm(): FormGroup {
    return this.fb.group({
      firstName: [
        this.data.user.firstname,
        Validators.compose([Validators.required]),
      ],
      lastName: [
        this.data.user.lastname,
        Validators.compose([Validators.required]),
      ],
      bio: [
        this.data.user.bio,
        Validators.compose([Validators.required]),
      ],
      username: [
        this.data.user.username,
        Validators.compose([Validators.required]),
      ]
    });
  }

  submit(): void {

    this.userService.updateUser(this.updateUser())
      .subscribe(() => this.notificationService.showSnackBar('User updated successfully'));
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  private updateUser(): User {
    this.data.user.firstname = this.profileEditForm.value.firstName;
    this.data.user.lastname = this.profileEditForm.value.lastName;
    this.data.user.bio = this.profileEditForm.value.bio;
    this.data.user.username = this.profileEditForm.value.username;
    return this.data.user;
  }
}
