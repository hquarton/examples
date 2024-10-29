export class Tutorial {
  id?: any;
  title?: string;
  description?: string;
  published?: boolean;
  operation?:string;
  createdDate?: Date;
  modifiedDate?: Date;
  lastPublishedDate?: Date;
  version?:number;
  histories?: History[];
}

export class History {
  id?: any;
  field?: string;
  before?: string;
  after?: string;
  changeDate?: Date;
}
