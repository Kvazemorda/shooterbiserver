package com.bean;

import com.HibernateSessionFactory;
import com.entity.ShooterEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.ArrayList;

@Local
@Stateless
public class DataEditor {
    private Session session;

    public DataEditor() {
        session = HibernateSessionFactory.getSessionFactory().openSession();
    }

    //метод проверяет юзера в базе и добавляет нового юзера если нету.
    // Возвращается false если юзер есть
    public boolean isShooterExist(long id){
        if(userExist(id)){
            return true;
        }else {
            return false;
        }
    }

    public ShooterEntity createTheNewShooter(){
        ShooterEntity shooterEntity = new ShooterEntity();
        saveToData(shooterEntity);

        if(userExist(shooterEntity.getId())){
            return shooterEntity;
        }else {
            return null;
        }
    }

    //метод сохраняет стрелка в базе
    private void saveToData(ShooterEntity shooter){
        session.beginTransaction();
        session.saveOrUpdate(shooter);
        session.getTransaction().commit();
        clearSession();
    }

    //метод проверяет наличие юзера в базе
    private boolean userExist(long idUser){
        String hql ="select shooters from ShooterEntity shooters " +
                "where shooters.id = :user";

        Query query = session.createQuery(hql);
        query.setParameter("user", idUser);
        if(query.list().size() > 0){
            return true;
        }else {
            return false;
        }
    }

    private void clearSession(){
        session.clear();
    }

    /**
     * Метод добавляет стрелку кол-во выстрелов и кол-во попаданий.
     * Если юзер не найден, то добавляет юзера в базу и добавляет выстрелы
     * @param shooter id стрелка, которому добавятся выстрелы
     * @param hits попадания
     * @param shoots кол-во выстрелов
     * @param isStand стрельба стоя
     * @return возвращает успешность добавления
     */
    public boolean addHitToShooter(ShooterEntity shooter, int hits, int shoots, boolean isStand){
        session.clear();
        if(isStand){
            shooter.setStandHit(shooter.getStandHit() + hits);
            shooter.setStandShootCount(shooter.getStandShootCount() + shoots);
            shooter.setStandStat(shooter.getStandHit() / (double) shooter.getStandShootCount());
        }else {
            shooter.setLyingHit(shooter.getLyingHit() + hits);
            shooter.setLyingShootCount(shooter.getLyingShootCount() + shoots);
            shooter.setLyingStat(shooter.getLyingHit() / (double) shooter.getLyingShootCount());
        }
        shooter.setGeneralStat((shooter.getLyingStat() + shooter.getStandStat())/ 2);
        updateShooter(shooter);

        return true;
    }

    /**
     * Метод добавляет имя шутера в базу
     * @param shooterId id стрелка
     * @param name имя его
     * @return true если имя добавлено
     */
    public boolean addNameOfShooter(long shooterId, String name){
        ShooterEntity shooter = getTheShooter(shooterId);
        shooter.setName(name);
        updateShooter(shooter);

        return true;
    }

    private void updateShooter(ShooterEntity shooter) {

        session.beginTransaction();
        session.update(shooter);
        session.getTransaction().commit();
        clearSession();
    }

    public ShooterEntity getTheShooter(long shooterId) {
        String hql = "select shooter from ShooterEntity shooter " +
                "where shooter.id = :shooterId";

        Query query = session.createQuery(hql);
        query.setParameter("shooterId", shooterId);
        if(query.list().size() > 0){
            return (ShooterEntity) query.list().get(0);
        }else {
            return null;
        }

    }

    /**
     * Возвращает 10 лучших стрелков чем стрелок который запрашивает эту информацию.
     * @param shooterId
     * @return
     */
    public ArrayList<ShooterEntity> tenOfBetterThenShooter(long shooterId){

        String hql = "select shooter from ShooterEntity shooter order by shooter.generalStat desc";
        Query query = session.createQuery(hql);
        ArrayList<ShooterEntity> listOfAllShooters = (ArrayList<ShooterEntity>) query.list();
        int indexShooter = listOfAllShooters.lastIndexOf(getTheShooter(shooterId));
        if(listOfAllShooters.size() != 0 && listOfAllShooters.size() >= indexShooter) {
            if (indexShooter - 10 > 0) {
                ArrayList<ShooterEntity> list10shooters =
                        new ArrayList<>(listOfAllShooters.subList(indexShooter - 10, indexShooter));
                return list10shooters;
            } else {
                if (indexShooter < 10 && listOfAllShooters.size() <= 10) {
                    ArrayList<ShooterEntity> list10shooters =
                            new ArrayList<>(listOfAllShooters.subList(0, listOfAllShooters.size()));
                    return list10shooters;
                }else if (listOfAllShooters.size() > 10) {
                    ArrayList<ShooterEntity> list10shooters =
                            new ArrayList<>(listOfAllShooters.subList(0, 10));
                    return list10shooters;
                }
            }
        }else {
            return listOfAllShooters;
        }
        return listOfAllShooters;
    }
}
