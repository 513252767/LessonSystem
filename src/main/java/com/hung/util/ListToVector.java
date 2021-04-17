package com.hung.util;

import com.hung.dao.PartDao;
import com.hung.entity.Report;
import com.hung.pojo.Comment;
import com.hung.pojo.Matter;
import com.hung.pojo.Part;
import com.hung.service.serviceImpl.MatterServiceImpl;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author Hung
 */
public class ListToVector {
    /**
     * matter转换
     *
     * @param list
     * @return
     */
    public static Vector<Vector> mListToVector(List<Matter> list) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            Matter matter = list.get(i);
            Map map = new LinkedHashMap();
            map.put("id", matter.getId());
            map.put("content", matter.getContent());
            map.put("uid", matter.getUid());
            map.put("postTime", matter.getPostTime());
            map.put("commentId", matter.getCommentId());
            map.put("praise", matter.getPraise());
            map.put("pid", matter.getPid());
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

    /**
     * comment转换
     *
     * @param comments
     * @return
     */
    public static Vector<Vector> cListToVector(List<Comment> comments) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            Map map = new LinkedHashMap<>();
            map.put("id", comment.getId());
            map.put("nickName", comment.getNickName());
            map.put("comment", comment.getComment());
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

    /**
     * report转换
     *
     * @param reports
     * @return
     */
    public static Vector<Vector> rListToVector(List<Report> reports) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);
            Map map = new LinkedHashMap<>();
            map.put("id", report.getId());
            map.put("mid", report.getMid());
            Matter matter = new MatterServiceImpl().queryMatterById(report.getMid());
            PartDao partDao = (PartDao) new DefaultSqlSession<>().getMapper(PartDao.class);
            Part part = partDao.queryPartById(report.getPid());
            map.put("content", matter.getContent());
            map.put("partName", part.getName());
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

    /**
     * parts转换
     *
     * @param parts
     * @return
     */
    public static Vector<Vector> pListToVector(List<Part> parts) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            Map map = new LinkedHashMap<>();
            map.put("id", part.getId());
            map.put("name", part.getName());
            map.put("createTime", part.getCreateTime());
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }
}
