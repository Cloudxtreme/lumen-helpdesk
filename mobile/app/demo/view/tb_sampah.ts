import {Component,OnInit,ViewEncapsulation} from '@angular/core';
import {CarService} from '../service/carservice';
import {SampahService} from '../service/sampahservice';
import {NodeService} from '../service/nodeservice';
import {EventService} from '../service/eventservice';
import {Car} from '../domain/car';
import {Sampah} from '../domain/sampah';
import {TreeNode} from 'primeng/primeng';

@Component({
    templateUrl: 'app/demo/view/tb_sampah.html',
    styles: [`                
        .cars-datalist ul {
            margin: 0;
            padding: 0;
        }
    
        @media (max-width:640px) {
            .cars-datalist .text-column {
                text-align: center;
            }
        }
    `],
    encapsulation: ViewEncapsulation.None
})
export class SampahDemo implements OnInit {

    cars: Car[];
    
    selectedCar: Car;
    
    sourceCars: Car[];
    
    targetCars: Car[];
    
    orderListCars: Car[];
    
    carouselCars: Car[];

    sampah: Sampah[];

    selectedSampah: Sampah[];

    sourceSampah: Sampah[];

    targetSampah: Sampah[];

    orderListSampahs: Sampah[];

    carouselSampahs: Sampah[];
    
    files1: TreeNode[];
    
    files2: TreeNode[];
    
    events: any[];
    
    selectedNode: TreeNode;
    
    scheduleHeader: any;

    //constructor(private carService: CarService, private eventService: EventService, private nodeService: NodeService) { }
    constructor(private sampahService: SampahService, private eventService: EventService, private nodeService: NodeService){}
    
    ngOnInit() {
        //this.carService.getCarsMedium().then(cars => this.cars = cars);
        this.sampahService.getSampahMedium().then(sampah => this.sampah = sampah);
        this.sampahService.getSampahMedium().then(sampah => this.sampah = sampah);
        //this.carService.getCarsMedium().then(cars => this.sourceCars = cars);
        //this.targetCars = [];
        this.targetSampah = [];
        //this.carService.getCarsSmall().then(cars => this.orderListCars = cars);
        this.sampahService.getSampahSmall().then(sampah => this.orderListSampahs = sampah);
        this.nodeService.getFilesystem().then(files => this.files1 = files);
        this.nodeService.getFiles().then(files => this.files2 = files);
        this.eventService.getEvents().then(events => {this.events = events;});
        
        //this.sam


        this.carouselSampahs = [
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'},
            {id_lapor: '22022', pelapor: '628214771xxxx', area: 'Nusa Tenggara Timur', tanggal_lapor: '7/27/2012 1:00', judul_lapor: 'Tenaga Dokter di Nusa Tenggara Timur'}
        ],
        
        this.scheduleHeader = {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		};
    }
}