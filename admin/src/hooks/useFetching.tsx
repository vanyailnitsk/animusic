import {useState} from "react";
export interface useFetchingResult{
    fetching:() => Promise<void>
    isLoading:boolean
    error:string
}
export type CallbackFunc = () => Promise<void>
export function useFetching(callback : CallbackFunc) : useFetchingResult {
    const [isLoading,setIsLoading] = useState<boolean>(false)
    const [error,setError] = useState<string>('')
    const fetching = async () : Promise<void> => {
        try {
            setIsLoading(true)
            await callback()
        }
        catch (e : any){
            setError(e.message)
        }
        finally {
            setIsLoading(false)
        }
    }
    return {fetching,isLoading,error}
}