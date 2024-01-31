import {Card} from "./card";

export interface Biodata{
    id: number;
    nama: string;
    tempatLahir: string;
    tanggalLahir: Date;
    jk: 'MALE' | 'FEMALE';
    agama: string;
    alamat: string;
    email: string;
    hobi: string;
    anakKe: number;
    jmlSaudara: number;
    statusKawin: 'BELUM_KAWIN' | 'KAWIN' | 'JANDA' | 'DUDA';
    statusVerifikasi: boolean;
    createdAt: Date;
    cards?: Card[];
}