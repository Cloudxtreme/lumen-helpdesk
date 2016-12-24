import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Sampah} from '../domain/sampah';

@Injectable()
export class SampahService {
    
    constructor(private http: Http) {}

    getSampahSmall() {
        return this.http.get('resources/demo/data/sampahs-small.json')
                    .toPromise()
                    .then(res => <Sampah[]> res.json().data)
                    .then(data => { return data; });
    }

    getSampahMedium() {
        return this.http.get('resources/demo/data/sampahs-medium.json')
                    .toPromise()
                    .then(res => <Sampah[]> res.json().data)
                    .then(data => { return data; });
    }

    getSampahLarge() {
        return this.http.get('resources/demo/data/sampahs-large.json')
                    .toPromise()
                    .then(res => <Sampah[]> res.json().data)
                    .then(data => { return data; });
    }
}