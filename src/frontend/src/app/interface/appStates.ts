import {DataState} from "../enum/dataStates.enum";
import {User} from "./user";
import {Events} from "./event";
import {Role} from "./role";
import {Biodata} from "./biodata";

export interface LoginState{
    dataState: DataState;
    loginSuccess?: boolean;
    error?: string;
    message?: string;
    isUsingMfa?: boolean;
    phone?: string;
}
export interface CustomHttpResponse<T> {
    timestamp: Date;
    statusCode: number;
    status: string;
    message: string;
    reason?: string;
    developerMessage?: string;
    data?: T;
}
export interface Profile {
    user: User;
    events?: Events[];
    roles?: Role[];
    access_token?: string;
    refresh_token?: string;
}
export interface Page<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    numberOfElements: number;
    size: number;
    number: number;
}
export interface BiodataState {
    user: User;
    biodata: Biodata;
}
export interface RegisterState {
    dataState: DataState;
    registerSuccess?: boolean;
    error?: string;
    message?: string;
}

export type AccountType = 'account' | 'password';

export interface VerifySate {
    dataState: DataState;
    verifySuccess?: boolean;
    error?: string;
    message?: string;
    title?: string;
    type?: AccountType;
}
