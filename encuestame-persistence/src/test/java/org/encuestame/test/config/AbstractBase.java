/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.config;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.dao.IClientDao;
import org.encuestame.persistence.dao.IEmail;
import org.encuestame.persistence.dao.IGeoPoint;
import org.encuestame.persistence.dao.IGeoPointTypeDao;
import org.encuestame.persistence.dao.IGroupDao;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.INotification;
import org.encuestame.persistence.dao.IPermissionDao;
import org.encuestame.persistence.dao.IPoll;
import org.encuestame.persistence.dao.IProjectDao;
import org.encuestame.persistence.dao.IQuestionDao;
import org.encuestame.persistence.dao.ISocialProviderDao;
import org.encuestame.persistence.dao.ISurvey;
import org.encuestame.persistence.dao.ISurveyFormatDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.dao.imp.ClientDao;
import org.encuestame.persistence.dao.imp.EmailDao;
import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.dao.imp.PollDao;
import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.Client;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.Status;
import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.Group.Type;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.domain.survey.QuestionAnswer;
import org.encuestame.persistence.domain.survey.QuestionColettion;
import org.encuestame.persistence.domain.survey.QuestionPattern;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyGroup;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollFolder;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.persistence.domain.survey.QuestionAnswer.AnswerType;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Base Class to Test Cases.
 * @author Picado, Juan juan@encuestame.org
 * @since October 15, 2009
 * @version $Id$
 */

public abstract class AbstractBase extends AbstractConfigurationBase{

    /**
     * Hibernate Template.
     */
    @Autowired
    private HibernateTemplate hibernateTemplate;

     /** SurveyFormat  Dao.**/
    @Autowired
    private ISurveyFormatDao surveyformatDaoImp;

    /** User Security Dao.**/
    @Autowired
    private IAccountDao accountDao;

    /**Group Security Dao.**/
    @Autowired
    private IGroupDao groupDaoImp;

    /** Security Permissions Dao.**/
    @Autowired
    private IPermissionDao permissionDaoImp;

    /** Catalog Location Dao.**/
    @Autowired
    private IGeoPoint geoPointDao;

    /** Project Dao Imp.**/
    @Autowired
    private IProjectDao projectDaoImp;

    /** Survey Dao Imp.**/
    @Autowired
    private ISurvey surveyDaoImp;

    /** Question Dao Imp.**/
    @Autowired
    private IQuestionDao questionDaoImp;

    /** Catalog Location Type Dao.**/
    @Autowired
    private IGeoPointTypeDao geoPointTypeDao;

    /** {@link ClientDao}. **/
    @Autowired
    private IClientDao clientDao;

    /** {@link TweetPollDao}. **/
    @Autowired
    private ITweetPoll iTweetPoll;

    /** {@link PollDao}. **/
    @Autowired
    private IPoll iPoll;

    /** {@link EmailDao}. **/
    @Autowired
    private IEmail emailDao;

    /** {@link Notification}. **/
    @Autowired
    private INotification notificationDao;

      /** Activate Notifications.**/
    private Boolean activateNotifications = false;

    /** Url Poll. **/
    public final String URLPOLL = "http://www.encuestame.org";

    /** Social Account Dao.**/
    @Autowired
    private ISocialProviderDao providerDao;

    /** {@link HashTagDao} **/
    @Autowired
    private IHashTagDao hashTagDao;

    /**
     * Get Property.
     * @param property
     * @return
     */
    public String getProperty(final String property){
        Resource resource = new FileSystemResource("src/main/resources/test-config.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("Property ["+property+"] value ["+props.getProperty(property)+"]");
        return props.getProperty(property);
    }


    /**
     * Getter.
     * @return the surveyFormatDaoImp
     */
    public ISurveyFormatDao getSurveyFormatDaoImp() {
        return surveyformatDaoImp;
    }

    /**
     * @param surveyformatDaoImp {@link ISurveyFormatDao}
     */
    public void setSurveyFormatDaoImp(final ISurveyFormatDao surveyformatDaoImp) {
        this.surveyformatDaoImp = surveyformatDaoImp;
    }

    /**
     * Flush Indexes.
     */
    public void flushIndexes(){
        final FullTextSession fullTextSession = Search.getFullTextSession(getHibernateTemplate().getSessionFactory().getCurrentSession());
        fullTextSession.flushToIndexes();
    }

    /**
     * @return the userDao
     */
    public IAccountDao getAccountDao() {
        return accountDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setAccountDao(final IAccountDao userDao) {
        this.accountDao = userDao;
    }

    /**
     * @return {@link IGroupDao}
     */
    public IGroupDao getGroup(){
        return groupDaoImp;
    }

    /**
     * @param GroupDaoImp  {@link IGroupDao}
     */
    public void setgroupDao(final IGroupDao GroupDaoImp){
        this.groupDaoImp = groupDaoImp;
    }

    /**
     * @return the permissionDaoImp
     */
    public IPermissionDao getPermissionDaoImp() {
        return permissionDaoImp;
    }

    /**
     * @param permissionDaoImp the permissionDaoImp to set
     */
    public void setPermissionDaoImp(final IPermissionDao permissionDaoImp) {
        this.permissionDaoImp = permissionDaoImp;
    }

    /**
     * @return the groupDaoImp
     */
    public IGroupDao getGroupDaoImp() {
        return groupDaoImp;
    }

    /**
     * @param secGroupDaoImp the secGroupDaoImp to set
     */
    public void setGroupDaoImp(final IGroupDao groupDaoImp) {
        this.groupDaoImp = groupDaoImp;
    }

    /**
     * @return the projectDaoImp
     */
    public IProjectDao getProjectDaoImp() {
        return projectDaoImp;
    }

    /**
     * @param projectDaoImp the projectDaoImp to set
     */
    public void setProjectDaoImp(final IProjectDao projectDaoImp) {
        this.projectDaoImp = projectDaoImp;
    }

    /**
     * @return the surveyDaoImp
     */
    public ISurvey getSurveyDaoImp() {
        return surveyDaoImp;
    }

    /**
     * @param surveyDaoImp the surveyDaoImp to set
     */
    public void setSurveyDaoImp(final ISurvey surveyDaoImp) {
        this.surveyDaoImp = surveyDaoImp;
    }

    /**
     * @return the questionDaoImp
     */
    public IQuestionDao getQuestionDaoImp() {
        return questionDaoImp;
    }

    /**
     * @param questionDaoImp the questionDaoImp to set
     */
    public void setQuestionDaoImp(final IQuestionDao questionDaoImp) {
        this.questionDaoImp = questionDaoImp;
    }


    /**
     * @return the surveyformatDaoImp
     */
    public ISurveyFormatDao getSurveyformatDaoImp() {
        return surveyformatDaoImp;
    }

    /**
     * @param surveyformatDaoImp the surveyformatDaoImp to set
     */
    public void setSurveyformatDaoImp(ISurveyFormatDao surveyformatDaoImp) {
        this.surveyformatDaoImp = surveyformatDaoImp;
    }

    /**
     * @return the geoPointTypeDao
     */
    public IGeoPointTypeDao getGeoPointTypeDao() {
        return geoPointTypeDao;
    }

    /**
     * @param geoPointTypeDao the geoPointTypeDao to set
     */
    public void setGeoPointTypeDao(IGeoPointTypeDao geoPointTypeDao) {
        this.geoPointTypeDao = geoPointTypeDao;
    }



    /**
     * @return geoPointDao
     */
    public IGeoPoint getGeoPointDao() {
        return geoPointDao;
    }

    /**
     * @param geoPointDao geoPointDao
     */
    public void setGeoPointDao(IGeoPoint geoPointDao) {
        this.geoPointDao = geoPointDao;
    }

    /**
     * @return {@link GeoPoint}
     */
    public IGeoPoint getGeoPoint() {
        return geoPointDao;
    }

    /**
     * @param geoPoint {@link GeoPoint}
     */
    public void setGeoPoint(final IGeoPoint geoPoint) {
        this.geoPointDao = geoPoint;
    }

    /**
     * @return {@link Poll}
     */
    public IPoll getiPoll() {
        return iPoll;
    }

    /**
     * @param poll the iPoll to set
     */
    public void setiPoll(final IPoll poll) {
        this.iPoll = poll;
    }

    /**
     * @return the catEmailDao
     */
    public IEmail getCatEmailDao() {
        return emailDao;
    }


    /**
     * @param catEmailDao the catEmailDao to set
     */
    public void setCatEmailDao(IEmail catEmailDao) {
        this.emailDao = catEmailDao;
    }


    /**
     * Helper to create poll
     * @return poll
     */
    public Poll createPoll(final Date createdAt,
            final Question question,
            final Account secUser,
            final Boolean pollCompleted,
            final Boolean pollPublish
            ){
        final String pollHash = RandomStringUtils.randomAlphabetic(18);
        final Poll poll = new Poll();
        poll.setCreatedAt(createdAt);
        poll.setQuestion(question);
        poll.setPollHash(pollHash);         //should be unique
        poll.setPollOwner(secUser);
        poll.setPollCompleted(pollCompleted);
        poll.setPublish(pollPublish);
        getiPoll().saveOrUpdate(poll);
        return poll;

    }
    /**
      * Helper to create poll
      * @param createdDate
      * @param question
      * @param hash
      * @param currentUser
      * @param pollCompleted
      * @return
      */
    public Poll createPoll(
            final Date createdDate,
            final Question question,
            final String hash,
            final Account secUsers,
            final Boolean pollCompleted,
            final Boolean published){
        final Poll poll = new Poll();
        poll.setCreatedAt(createdDate);
        poll.setQuestion(question);
        poll.setPollHash(hash);
        poll.setPollOwner(secUsers);
        poll.setPollCompleted(pollCompleted);
        poll.setPublish(published);
        getiPoll().saveOrUpdate(poll);
        return poll;

    }

     /**
     * Helper to create Poll Result.
     * @param questionAnswer {@link QuestionAnswer}
     * @param poll {@link Poll}
     * @return state
     */
    public PollResult createPollResults(final QuestionAnswer questionAnswer, final Poll poll){
        final PollResult pollRes = new PollResult();
        pollRes.setAnswer(questionAnswer);
        pollRes.setIpaddress("127.0.0.1");
        pollRes.setPoll(poll);
        pollRes.setVotationDate(new Date());
        getiPoll().saveOrUpdate(pollRes);
        return pollRes;

    }

    /**
     * Create project.
     * @param name Project's name
     * @param descProject Project Description
     * @param infoProject Informations's Project
     * @param state Project's state
     * @param user user
     * @return {@link Project}
     */
    public Project createProject(
            final String name,
            final String descProject,
            final String infoProject,
            final Account user) {
          Project project = new Project();
          project.setProjectDateFinish(new Date());
          project.setProjectDateStart(new Date());
          project.setProjectInfo(infoProject);
          project.setProjectName("name");
          project.setLead(createSecondaryUser("test", createUser()));
          project.setProjectDescription(descProject);
          project.setUsers(user);
          getProjectDaoImp().saveOrUpdate(project);
          return project;
    }

    /**
     * Create {@link Client}.
     * @param name name
     * @param project {@link Project}
     * @return {@link Client}
     */
    public Client createClient(final String name, final Project project){
        final Client client = new Client();
        client.setClientName(name);
        client.setProject(project);
        client.setClientEmail("");
        client.setClientDescription("");
        client.setClientFax("");
        client.setClientTelephone("");
        client.setClientTwitter("");
        client.setClientUrl("");
        getClientDao().saveOrUpdate(client);
        return client;
    }


    /**
     * Helper to create Secondary User.
     * @param name user name
     * @param secUser {@link Account}
     * @return state
     */
    public UserAccount createSecondaryUser(
            final String name,
            final Account secUser){
        return createSecondaryUser(name, name+"-"+RandomStringUtils.randomNumeric(6)+"@users.com", secUser);
    }


    public UserAccount createSecondaryUserGroup(
            final String name,
            final Account secUser,
            final Group group){
        return createSecondaryUserGroup(name, name+"-"+RandomStringUtils.randomNumeric(6)+"@users.com", secUser, group);
    }



    /**
     * Create Secondary User.
     * @param name
     * @param email
     * @param secUser
     * @return
     */
    public UserAccount createSecondaryUser(
            final String name,
            final String email,
            final Account secUser) {
        final UserAccount user= new UserAccount();
        user.setCompleteName(name);
        user.setUsername(name);
        user.setPassword("12345");
        user.setUserEmail(email);
        user.setEnjoyDate(new Date());
        user.setInviteCode("xxxxxxx");
        user.setAccount(secUser);
        user.setUserStatus(true);
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Create Secondary User.
     * @param name
     * @param email
     * @param secUser
     * @return
     */
    public UserAccount createSecondaryUserGroup(
            final String name,
            final String email,
            final Account secUser,
            final Group group){
        final UserAccount user= new UserAccount();
        user.setCompleteName(name);
        user.setUsername(name);
        user.setPassword("12345");
        user.setUserEmail(email);
        user.setEnjoyDate(new Date());
        user.setInviteCode("xxxxxxx");
        user.setAccount(secUser);
        user.setUserStatus(true);
        user.setGroup(group);
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Create User.
     * @return {@link Account}
     */
    public Account createUser(){
        Account user = new Account();
        user.setTwitterAccount("testTWitterAccount");
        user.setTwitterPassword("testTwitterPwsd");
        getAccountDao().saveOrUpdate(user);
        return user;
    }
    /**
     * Create User.
     * @param twitterAccount account
     * @param twitterPassword password
     * @return {@link Account}
     */
    public Account createUser(final String twitterAccount, final String twitterPassword){
        Account user = new Account();
        user.setTwitterAccount(twitterAccount);
        user.setTwitterPassword(twitterPassword);
        getAccountDao().saveOrUpdate(user);
        return user;
    }

    /**
     * Helper to create LocationType.
     * @param locationTypeName locationTypeName
     * @return locationType
     */

    public GeoPointType createGeoPointType(final String locationTypeName){
        final GeoPointType catLocatType = new GeoPointType();
        catLocatType.setLocationTypeDescription(locationTypeName);
        catLocatType.setLocationTypeLevel(1);
        catLocatType.setUsers(createUser());
        getGeoPointTypeDao().saveOrUpdate(catLocatType);
        return catLocatType;
    }


    /**
     * Helper to create GeoPoint.
     * @param locDescription locDescription
     * @param locTypeName locTypeName
     * @param Level Level
     * @return location {@link GeoPointFolder}.
     */
    public GeoPoint createGeoPoint(
                       final String locDescription,
                       final String locTypeName,
                       final Integer Level,
                       final Account secUsers,
                       final GeoPointFolder geoPointFolder){
        final GeoPoint location = new GeoPoint();
        location.setLocationStatus(Status.ACTIVE);
        location.setLocationDescription(locDescription);
        location.setLocationLatitude(2F);
        location.setAccount(secUsers);
        location.setGeoPointFolder(geoPointFolder);
        location.setLocationLongitude(3F);
        location.setTidtype(createGeoPointType(locTypeName));
        getGeoPointDao().saveOrUpdate(location);
      return location;
    }

    /**
     * Create Default Location.
     * @param locDescription description.
     * @param locTypeName type
     * @param Level level
     * @param secUsers {@link Account}.
     * @return
     */
    public GeoPoint createGeoPoint(
            final String locDescription,
            final String locTypeName,
            final Integer Level,
            final Account secUsers){
    return this.createGeoPoint(locDescription, locTypeName, Level, secUsers, null);
    }


    /**
     * Helper to create Group.
     * @param groupname user name
     * @return state
     */
    public Group createGroups(final String groupname){
        return createGroups(groupname, this.createUser());
    }

    public Group createGroups(final String groupname, final Account secUser){
        final Group group = new Group();
        group.setAccount(secUser);
        group.setGroupName(groupname);
        group.setIdState(1L);
        group.setGroupType(Type.SECURITY);
        group.setGroupDescriptionInfo("First Group");
        getGroup().saveOrUpdate(group);
        return group;
    }

    /**
     * Helper to create Permission.
     * @param permissionName name
     * @return Permission
     */
    public Permission createPermission(final String permissionName){
        final Permission permission = new Permission();
        permission.setPermissionDescription(permissionName);
        permission.setPermission(EnMePermission.getPermissionString(permissionName));
        getPermissionDaoImp().saveOrUpdate(permission);
        return permission;
    }

    /**
     * Helper to add permission to user.
     * @param user user
     * @param permission permission
     */
    public void addPermissionToUser(final Account user, final Permission permission){
       // final SecUserPermission userPerId = new SecUserPermission();
       // final SecUserPermissionId id = new SecUserPermissionId();
       /// id.setIdPermission(permission.getIdPermission());
       // id.setUid(user.getUid());
       // userPerId.setId(id);
        //userPerId.setState(true);
       // getSecUserDao().saveOrUpdate(userPerId);
    }

    /**
     * Helper to add user to group.
     * @param user user
     * @param group group
     */
    public void addGroupUser(
            final UserAccount user,
            final Group group)
    {
       /* final SecGroupUserId id = new SecGroupUserId();
        id.setGroupId(group.getGroupId());
        id.setUid(user.getUid());
        final SecGroupUser secGroupUser = new SecGroupUser();
        secGroupUser.setSecGroupUserId(id);
         secGroupUser.setSecUsers(user);
        secGroupUser.setSecGroups(group);*/
       // getSecUserDao().assingGroupToUser(secGroupUser);
    }

    /**
     * Helper permission to group.
     * @param permission permission
     * @param group group
     */
    public void addPermissionToGroup(
            final Permission permission,
            final Group group)
    {
      //  final SecGroupPermissionId groupPermissionId = new SecGroupPermissionId();
       //// groupPermissionId.setGroupId(group.getGroupId());
     //   groupPermissionId.setIdPermission(permission.getIdPermission());
       // final SecGroupPermission groupPermission = new SecGroupPermission();
       // groupPermission.setId(groupPermissionId);
       // groupPermission.setSecGroups(group);
       // groupPermission.setSecPermission(permission);
       // getSecGroup().saveOrUpdate(groupPermission);
    }

    /**
     * Create question.
     * @param question question
     * @param patron patron
     * @return {@link Question}
     */
    public Question createQuestion(final String question, String patron){
        final Question questions = new Question();
        questions.setQidKey("1");
        questions.setQuestion(question);
        questions.setQuestionPattern(createQuestionPattern(patron));
        questions.setAccountQuestion(createUser());
        getQuestionDaoImp().saveOrUpdate(questions);
        return questions;
    }

    /**
     * Create Default Question.
     * @param questionName
     * @return
     */
    public Question createDefaultQuestion(final String questionName){
        return this.createQuestion(questionName, "radio");


    }

    /**
     * Create Question.
     * @param questionName
     * @param user
     * @return
     */
    public Question createQuestion(final String questionName, final Account user){
        final Question question =  createQuestion(questionName, "patter");
        question.setAccountQuestion(user);
        getQuestionDaoImp().saveOrUpdate(question);
        //log.info("user assigned "+question.getSecUsersQuestion().getUid());
        return question;
    }

    /**
     * Create question.
     * @param question question
     * @param patron patron
     * @param user user
     * @return {@link Question}
     */
    public Question createQuestion(final String question, final String patron, final Account user){
        final Question questions = new Question();
        questions.setQidKey("1");
        questions.setAccountQuestion(user);
        questions.setQuestion(question);
        questions.setQuestionPattern(createQuestionPattern(patron));
        getQuestionDaoImp().saveOrUpdate(questions);
        return questions;
    }

    /**
     * Create Question Answer.
     * @param answer answer
     * @param question question
     * @param hash hash
     * @return {@link QuestionAnswer}
     */
    public QuestionAnswer createQuestionAnswer(final String answer, final Question question, final String hash){
        final QuestionAnswer questionsAnswers = new QuestionAnswer();
        questionsAnswers.setAnswer(answer);
        questionsAnswers.setQuestions(question);
        questionsAnswers.setUniqueAnserHash(hash);
        questionsAnswers.setAnswerType(AnswerType.DEFAULT);
        getQuestionDaoImp().saveOrUpdate(questionsAnswers);
        //log.info("Q "+questionsAnswers.getQuestionAnswerId());
        return questionsAnswers;
    }

    /**
     * Create Patron Domain.
     * @param typePatron name of patron
     * @return {@link QuestionPattern}
     */
    public QuestionPattern createQuestionPattern(final String typePatron){
        final QuestionPattern patron = new QuestionPattern();
        patron.setDesQid("patron Html");
        patron.setPatternTemplate("1");
        patron.setLabelQid("1");
        patron.setPatternType(typePatron);
        patron.setLevel(1);
        patron.setFinallity("save");
        //TODO: need patron dao to save this domain.
        getQuestionDaoImp().saveOrUpdate(patron);
        return patron;
    }

    /**
     *Helper to Create Survey Group.
     * @param surveyGroupName surveyGroupName
     * @return {@link SurveyGroup}
     *
     **/
    public SurveyGroup createSurveyGroup(String surveyGroupName){
        final SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setDateCreate(new Date());
        surveyGroup.setGroupName(surveyGroupName);
        getSurveyDaoImp().saveOrUpdate(surveyGroup);
        return surveyGroup;

    }

    /**
     *Helper to Create Question Collection.
     * @param desCollection Collection Description
     * @return {@link QuestionColettion}
     *
     **/
    public QuestionColettion createQuestionCollect(String desCollection){
        final QuestionColettion qCollection = new QuestionColettion();
        qCollection.setCreationDate(new Date());
        qCollection.setDesColeccion(desCollection);
        qCollection.setSecUsers(createUser());
        getQuestionDaoImp().saveOrUpdate(qCollection);
        return qCollection;
    }
    /**
     * Helper to Create Surveys Format.
     * @return {@link SurveyFormat}
     * */
    public SurveyFormat createSurveyFormat(
            final String formatName,
            final Date createdDate
            ){
        final SurveyFormat sformat = new SurveyFormat();
        sformat.setDateCreated(createdDate);
        sformat.setSurveyFormatName(formatName);
        sformat.getSurveyGroups().add(createSurveyGroup("editors"));
        getSurveyformatDaoImp().saveOrUpdate(sformat);
        return sformat;
    }

    /**
     * Create Default Survey Format
     * @return
     */
    public SurveyFormat createDefaultSurveyFormat(){
          return this.createSurveyFormat("New", new Date());

    }

    //TODO: Create Helpers for Publicated and Non Publicated TweetPoll

    /**
     * Create TWeetPoll.
     * @param tweetId tweetId
     * @param closeNotification tweetId
     * @param resultNotification resultNotification
     * @param allowLiveResults allowLiveResults
     * @param publishTweetPoll publishTweetPoll
     * @param scheduleTweetPoll publishTweetPoll
     * @param scheduleDate scheduleDate
     * @param publicationDateTweet publicationDateTweet
     * @param completed completed
     * @param tweetOwner tweetOwner
     * @param question question
     * @return tweetPoll.
     */
    public TweetPoll createTweetPoll(
             Long tweetId,
             Boolean closeNotification,
             Boolean resultNotification,
             Boolean allowLiveResults,
             Boolean publishTweetPoll,
             Boolean scheduleTweetPoll,
             Date scheduleDate,
             Date publicationDateTweet,
             Boolean completed,
             Account tweetOwner,
             Question question){
        final TweetPoll tweetPoll = new TweetPoll();
        tweetPoll.setCloseNotification(closeNotification);
        tweetPoll.setResultNotification(resultNotification);
        tweetPoll.setAllowLiveResults(allowLiveResults);
        tweetPoll.setCompleted(completed);
        tweetPoll.setPublishTweetPoll(publishTweetPoll);
        tweetPoll.setQuestion(question);
        tweetPoll.setScheduleDate(scheduleDate);
        tweetPoll.setScheduleTweetPoll(scheduleTweetPoll);
        tweetPoll.setCreateDate(publicationDateTweet);
        tweetPoll.setFavourites(Boolean.TRUE);
        tweetPoll.setTweetOwner(tweetOwner);
        getTweetPoll().saveOrUpdate(tweetPoll);
        return tweetPoll;
    }

    /**
     * Create Published {@link TweetPoll}.
     * @param tweetOwner tweet owner
     * @param question question
     * @return {@link TweetPoll}
     */
    public TweetPoll createPublishedTweetPoll(final Account tweetOwner, final Question question){
       return createTweetPoll(12345L, false, false, false, true, true, new Date(), new Date(), false, tweetOwner, question);
    }

    /**
     * Create Not Published {@link TweetPoll}.
     * @param tweetOwner tweet owner
     * @param question question
     * @return {@link TweetPoll}
     */
    public TweetPoll createNotPublishedTweetPoll(final Account tweetOwner, final Question question){
       return createTweetPoll(null, false, false, false, false, false, new Date(), null, false, tweetOwner, question);
    }

    /**
     * Create {@link TweetPollSwitch}.
     * @param questionsAnswers  {@link QuestionAnswer}.
     * @param tweetPollDomain {@link TweetPoll}.
     * @return {@link TweetPollSwitch}.
     */
    public TweetPollSwitch createTweetPollSwitch(final QuestionAnswer questionsAnswers, final TweetPoll tweetPollDomain){
        final TweetPollSwitch tPollSwitch = new TweetPollSwitch();
        tPollSwitch.setAnswers(questionsAnswers);
        tPollSwitch.setTweetPoll(tweetPollDomain);
        tPollSwitch.setCodeTweet(questionsAnswers.getUniqueAnserHash());
        getTweetPoll().saveOrUpdate(tPollSwitch);
        return tPollSwitch;
    }

    /**
     * Create TweetPoll Result
     * @param tweetPollSwitch {@link TweetPollResult}
     * @param Ip ip address
     * @return {@link TweetPollResult}.
     */
    public TweetPollResult createTweetPollResult(final TweetPollSwitch tweetPollSwitch, final String Ip){
        final TweetPollResult tweetPollResult = new TweetPollResult();
        tweetPollResult.setIpVote(Ip);
        tweetPollResult.setTweetPollSwitch(tweetPollSwitch);
        tweetPollResult.setTweetResponseDate(new Date());
        getTweetPoll().saveOrUpdate(tweetPollResult);
        return tweetPollResult;
    }

    /**
     * Create Fast TweetPoll Votes.
     * @return tweet poll
     */
    public TweetPoll createFastTweetPollVotes(){
        final UserAccount secondary = createSecondaryUser("jhon", createUser());
        final Question question = createQuestion("who I am?", "");
        final QuestionAnswer questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
        final QuestionAnswer questionsAnswers2 = createQuestionAnswer("no", question, "12346");
        final TweetPoll tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question);
        final TweetPollSwitch pollSwitch1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
        final TweetPollSwitch pollSwitch2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
        createTweetPollResult(pollSwitch1, "192.168.0.1");
        createTweetPollResult(pollSwitch1, "192.168.0.2");
        createTweetPollResult(pollSwitch2, "192.168.0.3");
        createTweetPollResult(pollSwitch2, "192.168.0.4");
        //log.info("tw "+tweetPoll);
        return tweetPoll;
    }

    /**
     * Create {@link GeoPointFolder}.
     * @param type {@link GeoPointFolderType}.
     * @param locationFolderId folder Id
     * @param secUsers {@link Account}.
     * @param folderName name
     * @param locationFolder
     * @return {@link GeoPointFolder}.
     */
    public GeoPointFolder createGeoPointFolder(
            final GeoPointFolderType type,
            final Account secUsers,
            final String folderName,
            final GeoPointFolder locationFolder){
        final GeoPointFolder geoPointFolder = new GeoPointFolder();
        geoPointFolder.setFolderType(type);
        geoPointFolder.setLocationFolderName(folderName);
        geoPointFolder.setAccount(secUsers);
        geoPointFolder.setSubLocationFolder(locationFolder);
        getGeoPointDao().saveOrUpdate(geoPointFolder);
        return geoPointFolder;
    }

    /**
     * Helper Create Survey Section.
     * @param catState
     * @param descSection
     * @return
     */
    public SurveySection createSurveySection(
            final String descSection){
        final SurveySection surveySection = new SurveySection();
        surveySection.setDescSection(descSection);
        getSurveyDaoImp().saveOrUpdate(surveySection);
        surveySection.getQuestionSection().add(createDefaultQuestion("Why is your favourite movie"));
        surveySection.getQuestionSection().add(createDefaultQuestion("Where do you live"));
        surveySection.getQuestionSection().add(createDefaultQuestion("What do you do at home"));
        getSurveyDaoImp().saveOrUpdate(surveySection);
        return surveySection;
    }

    /**
     * Create Defaul Survey Pagination.
     * @param surveySection
     * @return
     */
    public SurveyPagination createDefaultSurveyPagination(final SurveySection surveySection){
        return this.createSurveyPagination(1, surveySection,this.createDefaultSurvey(this.createUser()));
    }

    /**
     * Create Survey Pagination.
     * @param pageNumber
     * @param surveySection
     * @return
     */
    public SurveyPagination createSurveyPagination(
            final Integer pageNumber,
            final SurveySection surveySection,
            final Survey survey){
        final SurveyPagination surveyPag = new SurveyPagination();
        surveyPag.setPageNumber(pageNumber);
        surveyPag.setSurveySection(surveySection);
        surveyPag.setSurvey(survey);
        return surveyPag;
    }

    /**
     * Create Default Survey.
     * @param secUsers
     * @return
     */
    public Survey createDefaultSurvey(final Account secUsers ){
        return this.createSurvey("", new Date(), new Date(), secUsers, new Date(), createDefaultSurveyFormat(),"FirstSurvey");
     }

    /**
     * Create {@link Survey}
     * @param complete
     * @param dateInterview
     * @param endDate
     * @param secUsers
     * @param startDate
     * @param surveyFormat
     * @return
     */
   public Survey createSurvey(
           final String complete,
           final Date dateInterview,
           final Date endDate,
           final Account secUsers,
           final Date startDate,
           final SurveyFormat surveyFormat,
           final String name

           ){
       final Survey survey = new Survey();
       survey.setName(name);
       survey.setComplete(complete);
       survey.setDateInterview(dateInterview);
       survey.setEndDate(endDate);
       survey.setSecUsers(secUsers);
       survey.setStartDate(startDate);
       survey.setSurveyFormat(surveyFormat);
       survey.setTicket(3);
       survey.setTicket(2);
       getSurveyDaoImp().saveOrUpdate(survey);
       return survey;
   }




    /**
     * Create Default List Email.
     * @param user
     * @param list
     * @return
     */
    public EmailList createDefaultListEmail(final Account user,final String list){
        return this.createListEmails(user, list, new Date());
    }

    /**
     * Create Default List Email.
     * @return
     */
    public EmailList createDefaultListEmail(){
        return this.createListEmails(createUser(), "default", new Date());
    }

    /**
     * Create Default Email List.
     * @param list list Name
     * @return
     */

    public EmailList createDefaultListEmail(final String list){
        return this.createListEmails(createUser(), list, new Date());
    }

    /**
     *Create Default Email List.
     * @param user
     * @return
     */
    public EmailList createDefaultListEmail(final Account user){
        return this.createListEmails(user, "default", new Date());
    }

    /**
     * Create Email List.
     * @return
     */
    public EmailList createListEmails(
                final Account users,
                final String listName,
                final Date createDate){
            final EmailList catListEmails = new EmailList();
            catListEmails.setCreatedAt(createDate);
            catListEmails.setListName(listName);
            catListEmails.setUsuarioEmail(users);
            getCatEmailDao().saveOrUpdate(catListEmails);
            return catListEmails;
    }

    /**
     * Create Default Emails.
     * @param email
     * @return
     */
    public Email createDefaultEmails(final String email){
        return this.createEmails(email, createDefaultListEmail());
    }

    /**
     * Create Default Emails.
     * @param email
     * @param listEmail
     * @return
     */
    public Email createDefaultEmails(final String email, final EmailList listEmail){
        return this.createEmails(email, listEmail);
    }
    /**
     * Create Emails.
     * @param email
     * @param list
     * @return
     */
    public Email createEmails(
                final String email,
                final EmailList list){
            final Email emails = new Email();
            emails.setEmail(email);
            emails.setIdListEmail(list);
            getCatEmailDao().saveOrUpdate(emails);
         return emails;
    }


    /**
     * Create {@link SocialAccount}.
     * @param consumerKey
     * @param consumerSecret
     * @param secretToken
     * @param secUsers
     * @param twitterAccount
     * @return
     */
    public SocialAccount createTwitterAccount(
            final String consumerKey,
            final String consumerSecret,
            final String token,
            final String secretToken,
            final Integer twitterPin,
            final Account secUsers,
            final String twitterAccount){
        final SocialAccount socialTwitterAccounts = new SocialAccount();
        socialTwitterAccounts.setConsumerKey(consumerKey);
        socialTwitterAccounts.setConsumerSecret(consumerSecret);
        socialTwitterAccounts.setToken(token);
        socialTwitterAccounts.setSecretToken(secretToken);
        socialTwitterAccounts.setSecUsers(secUsers);
        socialTwitterAccounts.setTwitterPin(twitterPin);
        socialTwitterAccounts.setVerfied(Boolean.FALSE);
        socialTwitterAccounts.setTwitterAccount(twitterAccount);
        socialTwitterAccounts.setTwitterPassword("not valid");
        getAccountDao().saveOrUpdate(socialTwitterAccounts);
        return socialTwitterAccounts;
     }

    /**
     * Create Default Setted User.
     * @param secUsers {@link Account}.
     * @return {@link SocialAccount}.
     */
    public SocialAccount createDefaultSettedTwitterAccount(final Account secUsers){
        return this.createTwitterAccount(getProperty("twitter.test.consumerKey"),
                getProperty("twitter.test.consumerSecret"),
                getProperty("twitter.test.token"),
                getProperty("twitter.test.tokenSecret"),
                12345,
                secUsers,
                getProperty("twitter.test.account"));
    }

  /**
   *
   * @param folderName
   * @param users
   * @return
   */
    public SurveyFolder createSurveyFolders(final String folderName, final Account users){
        final SurveyFolder surveyFolders = new SurveyFolder();
        surveyFolders.setCreatedAt(new Date());
        surveyFolders.setFolderName(folderName);
        surveyFolders.setUsers(users);
        getSurveyDaoImp().saveOrUpdate(surveyFolders);
        return surveyFolders;
    }


    /**
     * Create {@link PollFolder}.
     * @param folderName folder name
     * @param users {@link Account}
     * @return {@link PollFolder}.
     */
    public PollFolder createPollFolder(final String folderName, final Account users){
        final PollFolder folder = new PollFolder();
        folder.setCreatedAt(new Date());
        folder.setFolderName(folderName);
        folder.setUsers(users);
        getiPoll().saveOrUpdate(folder);
        return folder;
    }

    /**
     * Create TweetPoll Folder.
     * @param folderName
     * @param users
     * @return
     */
    public TweetPollFolder createTweetPollFolder(final String folderName, final Account users){
        final TweetPollFolder folder = new TweetPollFolder();
        folder.setCreatedAt(new Date());
        folder.setFolderName(folderName);
        folder.setUsers(users);
        getTweetPoll().saveOrUpdate(folder);
        return folder;
    }

    /**
     * Add TweetPoll to Folder.
     * @param folderId
     * @param username
     * @param tweetPollId
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public TweetPoll addTweetPollToFolder(final Long folderId, final Long userId, final Long tweetPollId) throws EnMeDomainNotFoundException{
        final TweetPollFolder tpfolder = getTweetPoll().getTweetPollFolderById(folderId);
        final TweetPoll tpoll = getTweetPoll().getTweetPollByIdandUserId(tweetPollId, userId);
        tpoll.setTweetPollFolder(tpfolder);
        getTweetPoll().saveOrUpdate(tpoll);
        return tpoll;
    }

    /**
     * Add Survey To Folder.
     * @param folderId
     * @param userId
     * @param surveyId
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public Survey addSurveyToFolder(final Long folderId, final Long userId, final Long surveyId) throws EnMeDomainNotFoundException{
        final SurveyFolder sfolder = getSurveyDaoImp().getSurveyFolderById(folderId);
        final Survey survey = getSurveyDaoImp().getSurveyByIdandUserId(surveyId, userId);
        survey.setSurveysfolder(sfolder);
        getSurveyDaoImp().saveOrUpdate(survey);
        return survey;
    }

    /**
     * Add Poll to Folder.
     * @param folderId
     * @param userId
     * @param pollId
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public Poll addPollToFolder(final Long folderId, final Long userId, final Long pollId) throws EnMeDomainNotFoundException{
        final PollFolder pfolder = getiPoll().getPollFolderById(folderId);
        final Poll poll = getiPoll().getPollByIdandUserId(pollId, userId);
        poll.setPollFolder(pfolder);
        getiPoll().saveOrUpdate(poll);
        return poll;
    }

    /**
     * @return the activateNotifications
     */
    public Boolean getActivateNotifications() {
        return activateNotifications;
    }

    /**
     * @param activateNotifications uthe activateNotifications to set
     */
    public void setActivateNotifications(Boolean activateNotifications) {
        this.activateNotifications = activateNotifications;
    }

    /**
     * @return the clientDao
     */
    public IClientDao getClientDao() {
        return clientDao;
    }

    /**
     * @param clientDao the clientDao to set
     */
    public void setClientDao(final IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * @return the iTweetPoll
     */
    public ITweetPoll getTweetPoll() {
        return iTweetPoll;
    }

    /**
     * @param iTweetPoll the iTweetPoll to set
     */
    public void setTweetPoll(final ITweetPoll iTweetPoll) {
        this.iTweetPoll = iTweetPoll;
    }


    /**
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }


    /**
     * @param hibernateTemplate the hibernateTemplate to set
     */
    public void setHibernateTemplate(final HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * Create Notification.
     * @param message message
     * @param secUser {@link Account}.
     * @param description {@link NotificationEnum}.
     */
    public Notification createNotification(final String message, final Account secUser, final NotificationEnum description){
         final Notification notification = new Notification();
         notification.setAdditionalDescription(message);
         notification.setCreated(new Date());
         notification.setDescription(description);
         notification.setReaded(Boolean.FALSE);
         notification.setAccount(secUser);
         getNotification().saveOrUpdate(notification);
         return notification;
    }

    /**
     * Create Hash Tag.
     * @param hashTagName
     * @return
     */
    public HashTag createHashTag(final String hashTagName){
        final HashTag hashTag = new HashTag();
        hashTag.setHashTag(hashTagName);
        getHashTagDao().saveOrUpdate(hashTag);
        return hashTag;
    }
    /**
     * @return the notification
     */
    public INotification getNotification() {
        return notificationDao;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(final INotification notification) {
        this.notificationDao = notification;
    }

    /**
     * @return the providerDao
     */
    public ISocialProviderDao getProviderDao() {
        return providerDao;
    }

    /**
     * @param providerDao the providerDao to set
     */
    public void setProviderDao(ISocialProviderDao providerDao) {
        this.providerDao = providerDao;
    }

    /**
     * @return the hashTagDao
     */
    public IHashTagDao getHashTagDao() {
        return hashTagDao;
    }

    /**
     * @param hashTagDao the hashTagDao to set
     */
    public void setHashTagDao(IHashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }
}
