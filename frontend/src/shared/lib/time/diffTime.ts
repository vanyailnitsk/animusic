import {DateTime} from "luxon";


export const CalculateDataOfAddition = (date:string) => {
    const dateTime = DateTime.fromISO(date)
    const now = DateTime.now()
    const differenceInMinutes = now.diff(dateTime, 'minutes').minutes
    const differenceInHours = now.diff(dateTime, 'hours').hours
    const differenceInDays = now.diff(dateTime, 'days').days;
    if(Math.floor(differenceInMinutes) === 0) return "just now"
    if(Math.floor(differenceInHours) === 0) return "recently"
    if(Math.floor(differenceInHours) < 24) return 'today'
    if(Math.floor(differenceInDays) === 1) return '1 day ago'
    return `${Math.floor(differenceInDays)} days ago`
}