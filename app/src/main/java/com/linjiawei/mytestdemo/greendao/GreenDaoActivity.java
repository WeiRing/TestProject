package com.linjiawei.mytestdemo.greendao;

import android.os.Bundle;
import android.util.Log;

import com.linjiawei.mytestdemo.MainActivity;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.greendao.dao.PersonDao;
import com.linjiawei.mytestdemo.greendao.entity.Person;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class GreenDaoActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_green_dao;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String titleName = getIntent().getExtras().getString(MainActivity.ACTIVITY_TITLE_NAME);
        setTitle(titleName);

        //数据库的详细使用，参照 http://blog.csdn.net/scorpio_gao/article/details/53048699

    }



    /////////////      以下是封装各种方法，但还未测试             //////////////////


    private void getPersonById(long id) {
//         user =getUserDao().load(1l);
//        Log.i("tag", "结果：" + user.getId() + "," + user.getName() + "," + user.getAge() + "," + user.getIsBoy() + ";");
        Person person = getPersonDao().load(id);
        if (person != null) {
            Log.i("tag", "结果：" + person.getId() + "," + person.getName() + "," + person.getAge() + "," + person.getSex() + ";");
        }
    }

    private void insertData() {
        //插入数据
        Person insertData = new Person(0,123123123,"用户",21,true);
        getPersonDao().insert(insertData);
    }

    private void updateData(Person person) {
        //更改数据
        List<Person> persons = queryN("=", person.getIDNumber() + "");
        Person per = new Person(persons.get(0).getId(), persons.get(0).getIDNumber(), "更改后的用户", persons.get(0).getAge(), persons.get(0).getSex());
        getPersonDao().update(per);
    }

    private void queryData() {
        //查询数据详细
        List<Person> persons = getPersonDao().loadAll();
        Log.i("tag", "当前数量：" + persons.size());
        for (int i = 0; i < persons.size(); i++) {
            Log.i("tag", "结果：" + persons.get(i).getId() + "," + persons.get(i).getName() + "," + persons.get(i).getAge() + "," + persons.get(i).getSex() + ";");
        }

    }

    private void queryDataBy() {////查询条件
        Query<Person> nQuery = getPersonDao().queryBuilder()
                //                .where(UserDao.Properties.Name.eq("user1"))//.where(UserDao.Properties.Id.notEq(999))
                .orderAsc(PersonDao.Properties.Age)//.limit(5)//orderDesc
                .build();
        List<Person> persons = nQuery.list();
        Log.i("tag", "当前数量：" + persons.size());
        for (int i = 0; i < persons.size(); i++) {
            Log.i("tag", "结果：" + persons.get(i).getId() + "," + persons.get(i).getName() + "," + persons.get(i).getAge() + "," + persons.get(i).getSex() + ";");
        }

        //        QueryBuilder qb = userDao.queryBuilder();
        //        qb.where(Properties.FirstName.eq("Joe"),
        //                qb.or(Properties.YearOfBirth.gt(1970),
        //                        qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
        //        List youngJoes = qb.list();
    }


    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数
     * @return             数据列表
     */
    public List<Person> queryN(String where, String... params){
        return getPersonDao().queryRaw(where, params);
    }

    /**
     * 根据用户信息,插件或修改信息
     * @param person              用户信息
     * @return 插件或修改的用户id
     */
    public long saveN(Person person){
        return getPersonDao().insertOrReplace(person);
    }

    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNLists(final List<Person> list){
        if(list == null || list.isEmpty()){
            return;
        }
        getPersonDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Person person = list.get(i);
                    getPersonDao().insertOrReplace(person);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote(){
        getPersonDao().deleteAll();
    }

    /**
     * 根据用户类,删除信息
     * @param person    用户信息类
     */
    public void deleteNote(Person person){
        getPersonDao().delete(person);
    }

    private PersonDao getPersonDao() {
        return GreenDaoManager.getDaoManger().getMDaoSession().getPersonDao();
    }



}
