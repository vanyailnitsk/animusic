export interface IUser{
    id:number
    username:string
    email:string
    enabled:boolean
    authorities:string | null
}