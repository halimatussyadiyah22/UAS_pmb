import {Biodata} from "./biodata";

export interface Card{
    id: number;
    username: string;
    password: string;
    tanggal: Date;
    waktu: string;
    ruang: string;
    biodatas: Biodata[];
}