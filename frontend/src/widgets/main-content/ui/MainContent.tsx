import styles from './main-content.module.css'
import {PageWrapper} from "@/shared/ui";

export const MainContent = ({page}) => {
    return (
        <div className={styles.main__content__wrapper}>
            <PageWrapper page={page}/>
        </div>
    );
};

