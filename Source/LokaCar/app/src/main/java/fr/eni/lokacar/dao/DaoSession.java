package fr.eni.lokacar.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import fr.eni.lokacar.dao.Client;
import fr.eni.lokacar.dao.Vehicule;
import fr.eni.lokacar.dao.Location;

import fr.eni.lokacar.dao.ClientDao;
import fr.eni.lokacar.dao.VehiculeDao;
import fr.eni.lokacar.dao.LocationDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig clientDaoConfig;
    private final DaoConfig vehiculeDaoConfig;
    private final DaoConfig locationDaoConfig;

    private final ClientDao clientDao;
    private final VehiculeDao vehiculeDao;
    private final LocationDao locationDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        clientDaoConfig = daoConfigMap.get(ClientDao.class).clone();
        clientDaoConfig.initIdentityScope(type);

        vehiculeDaoConfig = daoConfigMap.get(VehiculeDao.class).clone();
        vehiculeDaoConfig.initIdentityScope(type);

        locationDaoConfig = daoConfigMap.get(LocationDao.class).clone();
        locationDaoConfig.initIdentityScope(type);

        clientDao = new ClientDao(clientDaoConfig, this);
        vehiculeDao = new VehiculeDao(vehiculeDaoConfig, this);
        locationDao = new LocationDao(locationDaoConfig, this);

        registerDao(Client.class, clientDao);
        registerDao(Vehicule.class, vehiculeDao);
        registerDao(Location.class, locationDao);
    }
    
    public void clear() {
        clientDaoConfig.clearIdentityScope();
        vehiculeDaoConfig.clearIdentityScope();
        locationDaoConfig.clearIdentityScope();
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public VehiculeDao getVehiculeDao() {
        return vehiculeDao;
    }

    public LocationDao getLocationDao() {
        return locationDao;
    }

}
