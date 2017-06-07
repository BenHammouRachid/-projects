import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Nimbleways -Calcul durée-';
  resultat = "......";
  dateDebut ="";
  dateFin = "";



  diffJoursAction=function () {
    if(this.dateDebut == "" || this.dateFin == "")
       {
           alert("date(s) NON valide(s)");
       }
     else
       {
           this.resultat = this.diffJours(this.dateDebut,this.dateFin);
        }
  }



  diffJours =function (first, second) {
    var unJour = 24*60*60*1000;
    var dd = new Date(first);
    var df =  new Date(second);
    return Math.round(Math.abs((dd.getTime() - df.getTime())/(unJour)));

}


}
