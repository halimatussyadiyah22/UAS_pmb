import {DataState} from "../enum/dataStates.enum";

export interface State<T>{
    dataState: DataState;
    appData?: T;
    error?: string;
}