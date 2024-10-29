import {Component, Input, OnInit} from '@angular/core';
import {History} from "../../models/tutorial.model";

@Component({
  selector: 'app-history-details',
  templateUrl: './history-details.component.html',
  styleUrl: './history-details.component.css',
})
export class HistoryDetailsComponent implements OnInit {
  @Input()
  histories: History[] | undefined;

  visible = false;

  ngOnInit(): void {
  }

  getValueBefore(history: History) {
    return history?.before ? this.getValue(history.before) : "N.D.";
  }

  getValueAfter(history: History) {
    return history?.after ? this.getValue(history.after) : "N.D.";
  }

  private getValue(value: string) {
    if (this.isValidDate(value)) {
      // const date = new Date(Date.parse( value ));
      return this.getNumberToDateString(Date.parse(value));
    }
    return value.toString();
  }

  isValidDate = (str: any): boolean => {
    let timestamp = Date.parse(str);
    return (!isNaN(timestamp));
  }

  getDateChange(history: History): string {
    return this.getDateToString((history.changeDate as Date))
  }

  private getDateToString(changeDate: Date): string {
    const date = new Date(changeDate);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  }

  private getNumberToDateString(changeDate: number): string {
    const date = new Date(changeDate);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  }

  getHistories() : History[]{
    const histSort: History[] = (this.histories as History[]).sort((a, b) => {
      if (b.id >a.id) {
        return 1;
      }

      if (b.id < a.id) {
        return -1;
      }

      return 0;
    })
    return histSort;
  }

  toggleCollapse(): void {
    this.visible = !this.visible;
  }

  getVisibility() {
    if(this.visible){
      return "visible";
    }
    return "hidden";
  }

  getSymboleCollapse() {
    if(this.visible){
      return "˅";
    }
    return ">";
  }
}
