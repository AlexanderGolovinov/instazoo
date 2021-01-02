import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const COMMENT_API = 'http://localhost:8080/api/comment/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  addCommentToPost(postId: number, message: string): Observable<any> {
    return this.http.post(COMMENT_API + postId + '/create', {
      message: message
    }, httpOptions);
  }

  getCommentsToPost(postId: number): Observable<any> {
    return this.http.get(COMMENT_API + postId + '/all');
  }


  deleteComment(commentId: number): Observable<any> {
    return this.http.post(COMMENT_API + commentId + '/delete', null);
  }
}
