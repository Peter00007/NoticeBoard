package com.board.service.impl;

import com.board.dao.TypeDao;
import com.board.dao.impl.TypeDaoImpl;
import com.board.model.Type;
import com.board.service.TypeService;
import org.apache.log4j.Logger;

import java.util.List;

public class TypeServiceImpl implements TypeService {
    private TypeDao typeDao = new TypeDaoImpl();
    private static final Logger LOGGER = Logger.getLogger(TypeServiceImpl.class);

    @Override
    public List<Type> getAllType() {
        LOGGER.info("Get All Types");
        return typeDao.getAllType();
    }

    @Override
    public List<Type> getAllTypeByNotice(int noticeId) {
        LOGGER.info("Get All Types by Notice ID = {" + noticeId + "}");
        return typeDao.getAllTypeByNotice(noticeId);
    }

    @Override
    public void insertIntoNoticeTypes(int noticeId, int typeId) {
        LOGGER.info("Insert into Notice_Types Notice with ID = {" + noticeId + "} and Type with ID = {" + typeId + "}");
        typeDao.insertIntoNoticeTypes(noticeId, typeId);
    }

    @Override
    public Type create(Type type) {
        LOGGER.info("Create Type = {" + type + "}");
        return typeDao.create(type);
    }

    @Override
    public Type update(Type type) {
        LOGGER.info("Update Type = {" + type + "}");
        return typeDao.update(type);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.info("Delete Type with ID = {" + id + "}");
        typeDao.delete(id);
    }

    @Override
    public Type getById(Integer id) {
        LOGGER.info("Get Type By ID = {" + id + "}");
        return typeDao.getById(id);
    }
}
