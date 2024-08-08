import {useEffect, useState} from "react";
import {AxiosResponse} from "axios";

export const useFetching = <T>(requestCallback: () => Promise<AxiosResponse<T>>, deps: any[] = []) => {
    const [data, setData] = useState<null | T>(null);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<string>('');

    useEffect(() => {
        const fetching = async () => {
            setIsLoading(true);
            try {
                const response = await requestCallback();
                setData(response.data);
            } catch (e: any) {
                setError(e.message);
            } finally {
                setIsLoading(false);
            }
        };
        fetching();
    }, deps);

    return {data, isLoading, error};
};
