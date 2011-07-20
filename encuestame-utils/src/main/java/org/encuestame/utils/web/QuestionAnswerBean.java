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
package org.encuestame.utils.web;

import java.io.Serializable;

import org.encuestame.utils.ShortUrlProvider;

/**
 * Answer Bean.
 * @author Picado, Juan Carlos juanATencuestame.org
 * @since 01/06/2009 15:24:16
 */

public class QuestionAnswerBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = 1914430351701434773L;

    /** Answer Id. **/
    private Long answerId;

    /** Answer. **/
    private String answers;

    /** Answer Hash. **/
    private String answerHash;

    /** Url. **/
    private String url;

    /** Short url. **/
    private String shortUrl;

    /** Question Id. **/
    private	 Long questionId;

    /** Color. */
    private String color;


    /** short url type. **/
    private ShortUrlProvider shortUrlType;

    /**
     * Constructor.
     */
    public QuestionAnswerBean() {
    }

    /**
     * Constructor with answers.
     * @param answers
     */
    public QuestionAnswerBean(final String answers) {
        super();
        this.answers = answers;
    }

    /**
     * @return the answerId
     */
    public final Long getAnswerId() {
        return answerId;
    }

    /**
     * @param answerId
     *            the answerId to set
     */
    public final void setAnswerId(final Long answerId) {
        this.answerId = answerId;
    }

    /**
     * @return the answers
     */
    public final String getAnswers() {
        return answers;
    }

    /**
     * @param answers
     *            the answers to set
     */
    public final void setAnswers(final String answers) {
        this.answers = answers;
    }

    /**
     * @return the answerHash
     */
    public final String getAnswerHash() {
        return answerHash;
    }

    /**
     * @param answerHash
     *            the answerHash to set
     */
    public final void setAnswerHash(final String answerHash) {
        this.answerHash = answerHash;
    }

    /**
     * @return the questionId
     */
    public final Long getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public final void setQuestionId(final Long questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the shortUrl
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * @param shortUrl the shortUrl to set
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * @return the shortUrlType
     */
    public ShortUrlProvider getShortUrlType() {
        return shortUrlType;
    }

    /**
     * @param shortUrlType the shortUrlType to set
     */
    public void setShortUrlType(final ShortUrlProvider shortUrlType) {
        this.shortUrlType = shortUrlType;
    }



    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "QuestionAnswerBean [answerId=" + answerId + ", answers="
                + answers + ", answerHash=" + answerHash + ", url=" + url
                + ", shortUrl=" + shortUrl + ", questionId=" + questionId
                + ", shortUrlType=" + shortUrlType + "]";
    }
}
