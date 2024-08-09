import styles from './main-content.module.css'
import {PageWrapper} from "@/shared/ui";
import {FC, ReactNode} from "react";
interface MainContentProps{
    page:ReactNode
}
export const MainContent:FC<MainContentProps> = ({page}) => {
    return (
        <div className={styles.main__content__wrapper}>
            <PageWrapper page={page}/>
        </div>
    );
};

