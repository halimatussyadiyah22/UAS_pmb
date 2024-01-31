import {DataState} from "../enum/dataStates.enum";
import {Events} from "./event";
import {Role} from "./role";

export interface User{
    id: number;
    name: string;
    email: string;
    address?: string;
    phone?: string;
    imageUrl?: string;
    enabled: boolean;
    isNotLocked: boolean;
    usingMfa: boolean;
    createdAt?: Date;
    roleName: string;
    permissions: string;
}

