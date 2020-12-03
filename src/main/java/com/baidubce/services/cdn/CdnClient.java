/*
 * Copyright 2016-2019 Baidu, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.baidubce.services.cdn;

import com.baidubce.AbstractBceClient;
import com.baidubce.BceClientConfiguration;
import com.baidubce.BceClientException;
import com.baidubce.auth.SignOptions;
import com.baidubce.http.Headers;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.BceMetadataResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceRequest;
import com.baidubce.services.cdn.model.CdnRequest;
import com.baidubce.services.cdn.model.CdnResponse;
import com.baidubce.services.cdn.model.DescribeIpRequest;
import com.baidubce.services.cdn.model.DescribeIpResponse;
import com.baidubce.services.cdn.model.GetCacheQuotaRequest;
import com.baidubce.services.cdn.model.GetCacheQuotaResponse;
import com.baidubce.services.cdn.model.GetPrefetchStatusRequest;
import com.baidubce.services.cdn.model.GetPurgeStatusRequest;
import com.baidubce.services.cdn.model.GetPurgeStatusResponse;
import com.baidubce.services.cdn.model.GetStatAvgSpeedRequest;
import com.baidubce.services.cdn.model.GetStatAvgSpeedResponse;
import com.baidubce.services.cdn.model.GetStatFlowRequest;
import com.baidubce.services.cdn.model.GetStatFlowResponse;
import com.baidubce.services.cdn.model.GetStatHitRateRequest;
import com.baidubce.services.cdn.model.GetStatHitRateResponse;
import com.baidubce.services.cdn.model.GetStatHttpCodeRequest;
import com.baidubce.services.cdn.model.GetStatHttpCodeResponse;
import com.baidubce.services.cdn.model.GetStatMetricRequest;
import com.baidubce.services.cdn.model.GetStatMetricResponse;
import com.baidubce.services.cdn.model.GetStatPvRequest;
import com.baidubce.services.cdn.model.GetStatPvResponse;
import com.baidubce.services.cdn.model.GetStatSrcFlowRequest;
import com.baidubce.services.cdn.model.GetStatSrcFlowResponse;
import com.baidubce.services.cdn.model.GetStatTopRefererRequest;
import com.baidubce.services.cdn.model.GetStatTopRefererResponse;
import com.baidubce.services.cdn.model.GetStatTopUrlRequest;
import com.baidubce.services.cdn.model.GetStatTopUrlResponse;
import com.baidubce.services.cdn.model.GetStatUvRequest;
import com.baidubce.services.cdn.model.GetStatUvResponse;
import com.baidubce.services.cdn.model.ListDomainsRequest;
import com.baidubce.services.cdn.model.ListDomainsResponse;
import com.baidubce.services.cdn.model.OriginPeer;
import com.baidubce.services.cdn.model.PrefetchRequest;
import com.baidubce.services.cdn.model.PrefetchResponse;
import com.baidubce.services.cdn.model.PurgeRequest;
import com.baidubce.services.cdn.model.PurgeResponse;
import com.baidubce.services.cdn.model.PurgeTask;
import com.baidubce.services.cdn.model.SetDomainCacheTTLResponse;
import com.baidubce.services.cdn.model.SetDomainLimitRateRequest;
import com.baidubce.services.cdn.model.SetDomainOriginRequest;
import com.baidubce.services.cdn.model.SetHttpsConfigRequest;
import com.baidubce.services.cdn.model.cache.GetCacheDetailRequest;
import com.baidubce.services.cdn.model.cache.GetCacheRecordsResponse;
import com.baidubce.services.cdn.model.cache.GetPrefetchStatusResponse;
import com.baidubce.services.cdn.model.cache.PrefetchTask;
import com.baidubce.services.cdn.model.certificate.DelDomainCertResponse;
import com.baidubce.services.cdn.model.certificate.GetDomainCertResponse;
import com.baidubce.services.cdn.model.certificate.SetDomainCertRequest;
import com.baidubce.services.cdn.model.certificate.SetDomainCertResponse;
import com.baidubce.services.cdn.model.domain.CheckDomainValidResponse;
import com.baidubce.services.cdn.model.domain.CommonResponse;
import com.baidubce.services.cdn.model.domain.CreateDomainRequest;
import com.baidubce.services.cdn.model.domain.CreateDomainResponse;
import com.baidubce.services.cdn.model.domain.DeleteDomainRequest;
import com.baidubce.services.cdn.model.domain.DisableDomainRequest;
import com.baidubce.services.cdn.model.domain.DisableDomainResponse;
import com.baidubce.services.cdn.model.domain.DomainMiddleRequest;
import com.baidubce.services.cdn.model.domain.EnableDomainRequest;
import com.baidubce.services.cdn.model.domain.EnableDomainResponse;
import com.baidubce.services.cdn.model.domain.GetDomainAccessLimitResponse;
import com.baidubce.services.cdn.model.domain.GetDomainCacheFullUrlResponse;
import com.baidubce.services.cdn.model.domain.GetDomainCacheShareResponse;
import com.baidubce.services.cdn.model.domain.GetDomainCacheTTLRequest;
import com.baidubce.services.cdn.model.domain.GetDomainCacheTTLResponse;
import com.baidubce.services.cdn.model.domain.GetDomainClientIpResponse;
import com.baidubce.services.cdn.model.domain.GetDomainCompressResponse;
import com.baidubce.services.cdn.model.domain.GetDomainConfigRequest;
import com.baidubce.services.cdn.model.domain.GetDomainConfigResponse;
import com.baidubce.services.cdn.model.domain.GetDomainCorsResponse;
import com.baidubce.services.cdn.model.domain.GetDomainErrorPageResponse;
import com.baidubce.services.cdn.model.domain.GetDomainFileTrimResponse;
import com.baidubce.services.cdn.model.domain.GetDomainHSTSResponse;
import com.baidubce.services.cdn.model.domain.GetDomainHttpHeaderResponse;
import com.baidubce.services.cdn.model.domain.GetDomainIPv6DispatchResponse;
import com.baidubce.services.cdn.model.domain.GetDomainIpACLResponse;
import com.baidubce.services.cdn.model.domain.GetDomainLogRequest;
import com.baidubce.services.cdn.model.domain.GetDomainLogResponse;
import com.baidubce.services.cdn.model.domain.GetDomainMediaDragResponse;
import com.baidubce.services.cdn.model.domain.GetDomainMobileAccessResponse;
import com.baidubce.services.cdn.model.domain.GetDomainOCSPSwitchResponse;
import com.baidubce.services.cdn.model.domain.GetDomainOfflineModeSwitchResponse;
import com.baidubce.services.cdn.model.domain.GetDomainOriginProtocolRequest;
import com.baidubce.services.cdn.model.domain.GetDomainOriginProtocolResponse;
import com.baidubce.services.cdn.model.domain.GetDomainQUICSwitchResponse;
import com.baidubce.services.cdn.model.domain.GetDomainRangeSwitchResponse;
import com.baidubce.services.cdn.model.domain.GetDomainRefererACLResponse;
import com.baidubce.services.cdn.model.domain.GetDomainRetryOriginResponse;
import com.baidubce.services.cdn.model.domain.GetDomainSeoSwitchResponse;
import com.baidubce.services.cdn.model.domain.GetDomainTrafficLimitRequest;
import com.baidubce.services.cdn.model.domain.GetDomainTrafficLimitResponse;
import com.baidubce.services.cdn.model.domain.GetDomainUaAclRequest;
import com.baidubce.services.cdn.model.domain.GetDomainUaAclResponse;
import com.baidubce.services.cdn.model.domain.GetUserDomainResponse;
import com.baidubce.services.cdn.model.domain.GetUserDomainsRequest;
import com.baidubce.services.cdn.model.domain.HttpsConfig;
import com.baidubce.services.cdn.model.domain.RequestAuth;
import com.baidubce.services.cdn.model.domain.SetDomainAccessLimitRequest;
import com.baidubce.services.cdn.model.domain.SetDomainCacheFullUrlRequest;
import com.baidubce.services.cdn.model.domain.SetDomainCacheShareRequest;
import com.baidubce.services.cdn.model.domain.SetDomainCacheTTLRequest;
import com.baidubce.services.cdn.model.domain.SetDomainClientIpRequest;
import com.baidubce.services.cdn.model.domain.SetDomainCompressRequest;
import com.baidubce.services.cdn.model.domain.SetDomainCorsRequest;
import com.baidubce.services.cdn.model.domain.SetDomainErrorPageRequest;
import com.baidubce.services.cdn.model.domain.SetDomainFileTrimRequest;
import com.baidubce.services.cdn.model.domain.SetDomainFollowProtocolRequest;
import com.baidubce.services.cdn.model.domain.SetDomainHSTSRequest;
import com.baidubce.services.cdn.model.domain.SetDomainHttpHeaderRequest;
import com.baidubce.services.cdn.model.domain.SetDomainIPv6DispatchRequest;
import com.baidubce.services.cdn.model.domain.SetDomainIpACLRequest;
import com.baidubce.services.cdn.model.domain.SetDomainMediaDragRequest;
import com.baidubce.services.cdn.model.domain.SetDomainMobileAccessRequest;
import com.baidubce.services.cdn.model.domain.SetDomainOCSPRequest;
import com.baidubce.services.cdn.model.domain.SetDomainOfflineModeRequest;
import com.baidubce.services.cdn.model.domain.SetDomainOriginProtocolRequest;
import com.baidubce.services.cdn.model.domain.SetDomainQUICRequest;
import com.baidubce.services.cdn.model.domain.SetDomainRangeSwitchRequest;
import com.baidubce.services.cdn.model.domain.SetDomainRefererACLRequest;
import com.baidubce.services.cdn.model.domain.SetDomainRetryOriginRequest;
import com.baidubce.services.cdn.model.domain.SetDomainSeoSwitchRequest;
import com.baidubce.services.cdn.model.domain.SetDomainTrafficLimitRequest;
import com.baidubce.services.cdn.model.domain.SetDomainUaAclRequest;
import com.baidubce.services.cdn.model.domain.SetRequestAuthRequest;
import com.baidubce.services.cdn.model.dsa.GetDsaDomainListResponse;
import com.baidubce.services.cdn.model.dsa.SetDomainDsaRequest;
import com.baidubce.services.cdn.model.dsa.SetDsaRequest;
import com.baidubce.services.cdn.model.logmodel.GetDomainListLogRequest;
import com.baidubce.services.cdn.model.logmodel.GetDomainListLogResponse;
import com.baidubce.services.cdn.model.logmodel.GetDomainListLogTransRequest;
import com.baidubce.util.DateUtils;
import com.baidubce.util.HttpUtils;
import com.baidubce.util.JsonUtils;
import com.baidubce.util.Validate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Client for accessing CDN Services.
 * Created by sunyixing on 2016/1/9.
 * Update by changxing01 on 2019/10/15
 */

public class CdnClient extends AbstractBceClient {

    /**
     * The version information for Document service APIs as URI prefix.
     */
    private static final String VERSION = "v2";

    /**
     * The common URI prefix for domain operation.
     */
    private static final String DOMAIN = "domain";

    /**
     * The common URI prefix for statistic services.
     */
    private static final String STAT = "stat";

    /**
     * The common URI prefix for cache operation.
     */
    private static final String CACHE = "cache";

    /**
     * The common URI prefix for logmodel operation.
     */
    private static final String LOG = "logmodel";

    /**
     * The common URI prefix for utils operation.
     */
    private static final String UTILS = "utils";

    private static final String USER = "user";

    /**
     * Generate signature with specified headers.
     */
    private static final String[] HEADERS_TO_SIGN = {"host", "x-bce-date"};

    private static final HttpResponseHandler[] cdnHandlers = new HttpResponseHandler[]{
            new BceMetadataResponseHandler(),
            new BceErrorResponseHandler(),
            new BceJsonResponseHandler()
    };

    /**
     * Constructs a new Document client to invoke service methods on CDN.
     */
    public CdnClient() {
        this(new BceClientConfiguration());
    }

    /**
     * Constructs a new client using the client configuration to access CDN services.
     *
     * @param clientConfiguration The client configuration options controlling how this client
     *                            connects to Document services (e.g. proxy settings, retry counts, etc).
     */
    public CdnClient(BceClientConfiguration clientConfiguration) {
        super(clientConfiguration, cdnHandlers);
    }

    /**
     * Create a new domain acceleration.
     *
     * @param request The request containing user-defined domain information.
     * @return Result of the createDomain operation returned by the service.
     */
    public CreateDomainResponse createDomain(CreateDomainRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT, DOMAIN, request.getDomain());
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CreateDomainResponse.class);
    }

    /**
     * Enable an existing domain acceleration.
     *
     * @param domain The specified domain name.
     */
    public void enableDomain(String domain) {
        enableDomain(new EnableDomainRequest().withDomain(domain));
    }

    /**
     * Enable an existing domain acceleration.
     *
     * @param request The request containing user-defined domain information.
     * @return Result of the enableDomain operation returned by the service.
     */
    public EnableDomainResponse enableDomain(EnableDomainRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.POST, DOMAIN, request.getDomain());
        internalRequest.addParameter("enable", "");
        return invokeHttpClient(internalRequest, EnableDomainResponse.class);
    }

    /**
     * Disable an existing domain acceleration.
     *
     * @param domain Name of the domain.
     */
    public void disableDomain(String domain) {
        disableDomain(new DisableDomainRequest().withDomain(domain));
    }

    /**
     * Disable an existing domain acceleration.
     *
     * @param request The request containing user-defined domain information.
     * @return Result of the disableDomain operation returned by the service.
     */
    public DisableDomainResponse disableDomain(DisableDomainRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.POST, DOMAIN, request.getDomain());
        internalRequest.addParameter("disable", "");
        return invokeHttpClient(internalRequest, DisableDomainResponse.class);
    }

    /**
     * Delete an existing domain acceleration.
     *
     * @param domain Name of the domain.
     */
    public void deleteDomain(String domain) {
        deleteDomain(new DeleteDomainRequest().withDomain(domain));
    }

    /**
     * Delete an existing domain acceleration
     *
     * @param request The request containing user-defined domain information.
     * @return Result of the deleteDomain operation returned by the service.
     */
    public CommonResponse deleteDomain(DeleteDomainRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.DELETE, DOMAIN, request.getDomain());
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Returns a list of all CDN domains that the authenticated sender of the request owns.
     *
     * @return All of the CDN domains owned by the authenticated sender of the request.
     */
    public ListDomainsResponse listDomains() {
        return this.listDomains(new ListDomainsRequest());
    }

    /**
     * Returns a list of all CDN domains that the authenticated sender of the request owns.
     *
     * @param request The request containing all of the options related to the listing of domains.
     * @return All of the CDN domains owned by the authenticated sender of the request.
     */
    public ListDomainsResponse listDomains(ListDomainsRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET, DOMAIN);
        return invokeHttpClient(internalRequest, ListDomainsResponse.class);
    }

    /**
     * Return a list of user's all CDN domains that include domain and domain status
     * support domain name fuzzy matching filter and domain status filter
     *
     * @param status search domain status  (ALL | RUNNING | STOPPED | OPERATING)
     * @return a list of user's all CDN domains thats filter by status
     */
    public GetUserDomainResponse getUserDomains(String status) {
        return getUserDomains(new GetUserDomainsRequest().withStatus(status));
    }

    /**
     * Return a list of user's all CDN domains that include domain and domain status
     * support domain name fuzzy matching filter and domain status filter
     *
     * @param request The request containing all of the options related to the listing of domains.
     * @return a list of user's all CDN domains thats filter by status and rule
     */
    public GetUserDomainResponse getUserDomains(GetUserDomainsRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET, USER, "domains");
        internalRequest.addParameter("status", request.getStatus());

        if (request.getRule() != null) {
            internalRequest.addParameter("rule", request.getRule());
        }

        return invokeHttpClient(internalRequest, GetUserDomainResponse.class);
    }

    /**
     * Query whether the domain name can be added
     *
     * @param domain check domain
     * @return the result of check that include isValid and fail message
     */
    public CheckDomainValidResponse checkDomainValid(String domain) {
        return checkDomainValid(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Query whether the domain name can be added
     *
     * @param request The request containing check parameter domain.
     * @return the result of check that include isValid and fail message
     */
    public CheckDomainValidResponse checkDomainValid(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "valid");
        return invokeHttpClient(internalRequest, CheckDomainValidResponse.class);
    }

    /**
     * Get detailed information of a domain.
     *
     * @param domain Name of the domain.
     * @return getDomainConfig of the getDomainConfig operation returned by the service.
     */
    public GetDomainConfigResponse getDomainConfig(String domain) {
        return getDomainConfig(new GetDomainConfigRequest().withDomain(domain));
    }

    /**
     * Get detailed information of a domain.
     *
     * @param request The request containing all of the options related to the domain.
     * @return getDomainConfig of the getDomainConfig operation returned by the service.
     */
    public GetDomainConfigResponse getDomainConfig(GetDomainConfigRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        return invokeHttpClient(internalRequest, GetDomainConfigResponse.class);
    }

    /**
     * Update origin of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @param peer   The peer address of new origin.
     */
    public void setDomainOrigin(String domain, String peer) {
        List<OriginPeer> origin = new ArrayList<OriginPeer>();
        origin.add(new OriginPeer().withPeer(peer));
        SetDomainOriginRequest request = new SetDomainOriginRequest()
                .withDomain(domain)
                .withOrigin(origin);
        setDomainOrigin(request);
    }

    /**
     * Update origin of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainOrigin operation returned by the service.
     */
    public CommonResponse setDomainOrigin(SetDomainOriginRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("origin", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * The configuration protocol follows back to the source
     *
     * @param domain         Name of the domain.
     * @param followProtocol Whether the back source protocol is consistent with the request protocol
     * @return Result of the setDomainFollowProtocol operation returned by the service.
     */
    public CommonResponse setDomainFollowProtocol(String domain, boolean followProtocol) {
        return setDomainFollowProtocol(new SetDomainFollowProtocolRequest()
                .withDomain(domain)
                .withFollowProtocol(followProtocol));
    }

    /**
     * The configuration protocol follows back to the source
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainFollowProtocol operation returned by the service.
     */
    public CommonResponse setDomainFollowProtocol(SetDomainFollowProtocolRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("followProtocol", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Update RangeSwitch of specified domain acceleration.
     *
     * @param domain      domain's name
     * @param rangeSwitch The request containing all of the options related to the domain.
     * @return Result of the setDomainRangeSwitch operation returned by the service.
     */
    public CommonResponse setDomainRangeSwitch(String domain, boolean rangeSwitch) {
        return setDomainRangeSwitch(new SetDomainRangeSwitchRequest()
                .withDomain(domain)
                .withRangeSwitch(rangeSwitch));
    }

    /**
     * Update RangeSwitch of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainRangeSwitch operation returned by the service.
     */
    public CommonResponse setDomainRangeSwitch(SetDomainRangeSwitchRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("rangeSwitch", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get RangeSwitch of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain rangeSwitch.
     */
    public GetDomainRangeSwitchResponse getDomainRangeSwitch(String domain) {
        return getDomainRangeSwitch(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get RangeSwitch of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain rangeSwitch.
     */
    public GetDomainRangeSwitchResponse getDomainRangeSwitch(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("rangeSwitch", "");
        return invokeHttpClient(internalRequest, GetDomainRangeSwitchResponse.class);
    }

    /**
     * Update MobileAccess of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainMobileAccess operation returned by the service.
     */
    public CommonResponse setDomainMobileAccess(SetDomainMobileAccessRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("mobileAccess", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get MobileAccess of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain MobileAccess.
     */
    public GetDomainMobileAccessResponse getDomainMobileAccess(String domain) {
        return getDomainMobileAccess(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get MobileAccess of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain MobileAccess.
     */
    public GetDomainMobileAccessResponse getDomainMobileAccess(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("mobileAccess", "");
        return invokeHttpClient(internalRequest, GetDomainMobileAccessResponse.class);
    }

    /**
     * Update HttpHeader of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainHttpHeader operation returned by the service.
     */
    public CommonResponse setDomainHttpHeader(SetDomainHttpHeaderRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("httpHeader", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get HttpHeader of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain HttpHeader.
     */
    public GetDomainHttpHeaderResponse getDomainHttpHeader(String domain) {
        return getDomainHttpHeader(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get HttpHeader of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain HttpHeader.
     */
    public GetDomainHttpHeaderResponse getDomainHttpHeader(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("httpHeader", "");
        return invokeHttpClient(internalRequest, GetDomainHttpHeaderResponse.class);
    }

    /**
     * Update SeoSwitch of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainSeoSwitch operation returned by the service.
     */
    public CommonResponse setDomainSeoSwitch(SetDomainSeoSwitchRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("seoSwitch", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Update OCSP of specified domain acceleration.
     *
     * @param request domain and switch
     * @return
     */
    public CommonResponse setDomainOCSPSwitch(SetDomainOCSPRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT, DOMAIN,
                request.getDomain(), "config");
        internalRequest.addParameter("ocsp", "");
        attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainOCSPSwitchResponse getDomainOCSPSwitch(String domain) {
        return getDomainOCSPSwitch(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get ipv6Dispatch config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainOCSPSwitchResponse getDomainOCSPSwitch(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("ocsp", "");
        return invokeHttpClient(internalRequest, GetDomainOCSPSwitchResponse.class);
    }

    /**
     * Update QUIC of specified domain acceleration.
     *
     * @param domain
     * @param request
     * @return
     */
    public CommonResponse setDomainQUICSwitch(String domain, SetDomainQUICRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT, DOMAIN, domain, "config");
        internalRequest.addParameter("quic", "");
        attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainQUICSwitchResponse getDomainQUICSwitch(String domain) {
        return getDomainQUICSwitch(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get ipv6Dispatch config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainQUICSwitchResponse getDomainQUICSwitch(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("quic", "");
        return invokeHttpClient(internalRequest, GetDomainQUICSwitchResponse.class);
    }


    /**
     * Update offline mode of specified domain acceleration.
     *
     * @param domain
     * @param request
     * @return
     */
    public CommonResponse setDomainOfflineModeSwitch(String domain, SetDomainOfflineModeRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT, DOMAIN, domain, "config");
        internalRequest.addParameter("offlineMode", "");
        attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainOfflineModeSwitchResponse getDomainOfflineModeSwitch(String domain) {
        return getDomainOfflineModeSwitch(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get OfflineMode config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainOfflineModeSwitchResponse getDomainOfflineModeSwitch(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("offlineMode", "");
        return invokeHttpClient(internalRequest, GetDomainOfflineModeSwitchResponse.class);
    }

    /**
     * Get SeoSwitch of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain SeoSwitch.
     */
    public GetDomainSeoSwitchResponse getDomainSeoSwitch(String domain) {
        return getDomainSeoSwitch(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * add/update certificate of specified domain
     *
     * @param domain
     * @param request
     * @return
     */
    public SetDomainCertResponse setDomainCert(String domain, SetDomainCertRequest request) {
        Validate.checkStringNotEmpty(domain, "The parameter domain should NOT be empty.");
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                domain, "certificates");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, SetDomainCertResponse.class);
    }

    public DelDomainCertResponse deleteDomainCert(String domain) {
        return deleteDomainCert(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * delete certificate of specified domain
     *
     * @param request
     * @return
     */
    public DelDomainCertResponse deleteDomainCert(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.DELETE,
                request.getDomain(), "certificates");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, DelDomainCertResponse.class);
    }

    public GetDomainCertResponse getDomainCert(String domain) {
        return getDomainCert(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get Domain Cert detail information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainCertResponse getDomainCert(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                request.getDomain(), "certificates");
        return invokeHttpClient(internalRequest, GetDomainCertResponse.class);
    }

    /**
     * Update HSTS rules of specified domain acceleration.
     *
     * @param domain  domain
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainHSTS operation returned by the service.
     */
    public CommonResponse setDomainHSTS(String domain, SetDomainHSTSRequest request) {
        Validate.checkStringNotEmpty(domain, "The parameter domain should NOT be empty.");
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, domain, "config");
        internalRequest.addParameter("hsts", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainHSTSResponse getDomainHSTS(String domain) {
        return getDomainHSTS(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get ipv6Dispatch config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainHSTSResponse getDomainHSTS(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("hsts", "");
        return invokeHttpClient(internalRequest, GetDomainHSTSResponse.class);
    }

    /**
     * Update ipv6Dispatch of specified domain acceleration.
     *
     * @param domain
     * @param request
     * @return
     */
    public CommonResponse setDomainIPv6Dispatch(String domain, SetDomainIPv6DispatchRequest request) {
        Validate.checkStringNotEmpty(domain, "The parameter domain should NOT be empty.");
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, domain, "config");
        internalRequest.addParameter("ipv6Dispatch", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainIPv6DispatchResponse getDomainIPv6Dispatch(String domain) {
        return getDomainIPv6Dispatch(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get ipv6Dispatch config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainIPv6DispatchResponse getDomainIPv6Dispatch(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("ipv6Dispatch", "");
        return invokeHttpClient(internalRequest, GetDomainIPv6DispatchResponse.class);
    }

    /**
     * Update Domain cache share of specified domain acceleration.
     *
     * @param domain
     * @param request
     * @return
     */
    public CommonResponse setDomainCacheShare(String domain, SetDomainCacheShareRequest request) {
        Validate.checkStringNotEmpty(domain, "The parameter domain should NOT be empty.");
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, domain, "config");
        internalRequest.addParameter("cacheShare", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainCacheShareResponse getDomainCacheShare(String domain) {
        return getDomainCacheShare(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get cache share config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainCacheShareResponse getDomainCacheShare(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cacheShare", "");
        return invokeHttpClient(internalRequest, GetDomainCacheShareResponse.class);
    }

    /**
     * Get SeoSwitch of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain SeoSwitch.
     */
    public GetDomainSeoSwitchResponse getDomainSeoSwitch(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("seoSwitch", "");
        return invokeHttpClient(internalRequest, GetDomainSeoSwitchResponse.class);
    }

    /**
     * Update FileTrim of specified domain acceleration.
     *
     * @param domain   Name of the domain.
     * @param fileTrim Whether to enable page optimization
     */
    public void setDomainFileTrim(String domain, boolean fileTrim) {
        setDomainFileTrim(new SetDomainFileTrimRequest().withDomain(domain).withFileTrim(fileTrim));
    }

    /**
     * Update FileTrim of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainFileTrim operation returned by the service.
     */
    public CommonResponse setDomainFileTrim(SetDomainFileTrimRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("fileTrim", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get FileTrim of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain FileTrim.
     */
    public GetDomainFileTrimResponse getDomainFileTrim(String domain) {
        return getDomainFileTrim(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get FileTrim of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain FileTrim.
     */
    public GetDomainFileTrimResponse getDomainFileTrim(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("fileTrim", "");
        return invokeHttpClient(internalRequest, GetDomainFileTrimResponse.class);
    }

    /**
     * Update MediaDrag of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainMediaDrag operation returned by the service.
     */
    public CommonResponse setDomainMediaDrag(SetDomainMediaDragRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("mediaDrag", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get MediaDrag of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain MediaDrag.
     */
    public GetDomainMediaDragResponse getDomainMediaDrag(String domain) {
        return getDomainMediaDrag(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get MediaDrag of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain MediaDrag.
     */
    public GetDomainMediaDragResponse getDomainMediaDrag(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("mediaDrag", "");
        return invokeHttpClient(internalRequest, GetDomainMediaDragResponse.class);
    }

    /**
     * Update Compress of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainCompress operation returned by the service.
     */
    public CommonResponse setDomainCompress(SetDomainCompressRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("compress", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get Compress of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about domain Compress.
     */
    public GetDomainCompressResponse getDomainCompress(String domain) {
        return getDomainCompress(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get Compress of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about domain Compress.
     */
    public GetDomainCompressResponse getDomainCompress(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("compress", "");
        return invokeHttpClient(internalRequest, GetDomainCompressResponse.class);
    }

    /**
     * Get cache policies of specified domain acceleration.
     *
     * @param domain Name of the domain.
     * @return Detailed information about cache policies.
     */
    public GetDomainCacheTTLResponse getDomainCacheTTL(String domain) {
        GetDomainCacheTTLRequest request = new GetDomainCacheTTLRequest().withDomain(domain);
        return getDomainCacheTTL(request);
    }

    /**
     * Get cache policies of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Detailed information about cache policies.
     */
    public GetDomainCacheTTLResponse getDomainCacheTTL(GetDomainCacheTTLRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cacheTTL", "");
        return invokeHttpClient(internalRequest, GetDomainCacheTTLResponse.class);
    }

    /**
     * Update cache policies of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainCacheTTL operation returned by the service.
     */
    public SetDomainCacheTTLResponse setDomainCacheTTL(SetDomainCacheTTLRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cacheTTL", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, SetDomainCacheTTLResponse.class);
    }

    /**
     * Update retry origin of specified domain acceleration.
     *
     * @param domain
     * @param request
     * @return
     */
    public CommonResponse setDomainRetryOrigin(String domain, SetDomainRetryOriginRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, domain, "config");
        internalRequest.addParameter("retryOrigin", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainRetryOriginResponse getDomainRetryOrigin(String domain) {
        return getDomainRetryOrigin(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get retry origin config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainRetryOriginResponse getDomainRetryOrigin(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("retryOrigin", "");
        return invokeHttpClient(internalRequest, GetDomainRetryOriginResponse.class);
    }

    /**
     * Update cache policy of specified domain acceleration.
     *
     * @param domain  Name of the domain.
     * @param setting For true, treat the full URL as unique cache id, otherwise
     *                ignore query string parameters.
     */
    public void setDomainCacheFullUrl(String domain, boolean setting) {
        SetDomainCacheFullUrlRequest request = new SetDomainCacheFullUrlRequest()
                .withDomain(domain);
        request.setCacheFullUrl(setting);
        setDomainCacheFullUrl(request);
    }

    /**
     * Update cache policy of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainCacheFullUrl operation returned by the service.
     */
    public CommonResponse setDomainCacheFullUrl(SetDomainCacheFullUrlRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cacheFullUrl", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * search domain's rule of caching filter parameter
     *
     * @param domain Name of the domain.
     * @return domain's rule of cache filter parameter
     */
    public GetDomainCacheFullUrlResponse getDomainCacheFullUrl(String domain) {
        return getDomainCacheFullUrl(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * search domain's rule of caching filter parameter
     *
     * @param request The request containing all of the options related to the get cache full url request.
     * @return domain's rule of cache filter parameter
     */
    public GetDomainCacheFullUrlResponse getDomainCacheFullUrl(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cacheFullUrl", "");
        return invokeHttpClient(internalRequest, GetDomainCacheFullUrlResponse.class);
    }

    /**
     * add website error page to deal with exception.
     *
     * @param request The request containing all of the options related to the set request.
     * @return Result of the setDomainErrorPage operation returned by the service.
     */
    public CommonResponse setDomainErrorPage(SetDomainErrorPageRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("errorPage", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * search domain's custom error page
     *
     * @param domain Name of the domain.
     * @return custom error page info list
     */
    public GetDomainErrorPageResponse getDomainErrorPage(String domain) {
        return getDomainErrorPage(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * search domain's custom error page
     *
     * @param request The request containing all of the options related to the get error page request.
     * @return custom error page info list
     */
    public GetDomainErrorPageResponse getDomainErrorPage(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("errorPage", "");
        return invokeHttpClient(internalRequest, GetDomainErrorPageResponse.class);
    }

    /**
     * Update RefererACL rules of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainRefererACL operation returned by the service.
     */
    public CommonResponse setDomainRefererACL(SetDomainRefererACLRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("refererACL", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get RefererACL rules of specified domain acceleration.
     *
     * @param domain The request containing all of the options related to the get refererACL.
     * @return Result of the getDomainRefererACL operation returned by the service.
     */
    public GetDomainRefererACLResponse getDomainRefererACL(String domain) {
        return getDomainRefererACL(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get RefererACL rules of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the get refererACL.
     * @return Result of the getDomainRefererACL operation returned by the service.
     */
    public GetDomainRefererACLResponse getDomainRefererACL(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("refererACL", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, GetDomainRefererACLResponse.class);
    }

    /**
     * Update IpACL rules of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainIpACL operation returned by the service.
     */
    public CommonResponse setDomainIpACL(SetDomainIpACLRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("ipACL", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get IpACL rules of specified domain acceleration.
     *
     * @param domain The request containing all of the options related to the get IpACL.
     * @return Result of the getDomainIpACL operation returned by the service.
     */
    public GetDomainIpACLResponse getDomainIpACL(String domain) {
        return getDomainIpACL(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get IpACL rules of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the Get IpACL.
     * @return Result of the getDomainIpACL operation returned by the service.
     */
    public GetDomainIpACLResponse getDomainIpACL(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("ipACL", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, GetDomainIpACLResponse.class);
    }

    /**
     * Set the rate limit of specified domain acceleration.
     *
     * @param domain    Name of the domain.
     * @param limitRate The limit of downloading rate, in Bytes/s.
     */
    public void setDomainLimitRate(String domain, int limitRate) {
        SetDomainLimitRateRequest request = new SetDomainLimitRateRequest()
                .withDomain(domain)
                .withLimitRate(limitRate);
        setDomainLimitRate(request);
    }

    /**
     * Set the rate limit of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainLimitRate operation returned by the service.
     */
    public CommonResponse setDomainLimitRate(SetDomainLimitRateRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("limitRate", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Set the traffic limit of specified domain acceleration.
     * each response to client
     *
     * @param request
     * @return
     */
    public CommonResponse setDomainTrafficLimit(SetDomainTrafficLimitRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest =
                createRequest(request, HttpMethodName.PUT, DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("trafficLimit", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get trafficLimit of specified domain acceleration.
     * for each response
     *
     * @param domain
     * @return
     */
    public GetDomainTrafficLimitResponse getDomainTrafficLimit(String domain) {
        GetDomainTrafficLimitRequest request = new GetDomainTrafficLimitRequest().withDomain(domain);
        return this.getTrafficLimit(request);
    }

    /**
     * Get trafficLimit of specified domain acceleration.
     * for each response
     *
     * @param request
     * @return
     */
    public GetDomainTrafficLimitResponse getTrafficLimit(GetDomainTrafficLimitRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest =
                createRequest(request, HttpMethodName.GET, DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("trafficLimit", "");
        return invokeHttpClient(internalRequest, GetDomainTrafficLimitResponse.class);
    }

    /**
     * Set the UA ACL of specified domain acceleration.
     *
     * @param request
     * @return
     */
    public CommonResponse setDomainUaAcl(SetDomainUaAclRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest =
                createRequest(request, HttpMethodName.PUT, DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("uaAcl", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get UA ACL of specified domain acceleration.
     *
     * @param domain
     * @return
     */
    public GetDomainUaAclResponse getDomainUaAcl(String domain) {
        GetDomainUaAclRequest request = new GetDomainUaAclRequest().withDomain(domain);
        return this.getDomainUaAcl(request);
    }

    /**
     * Get UA ACL of specified domain acceleration.
     *
     * @param request
     * @return
     */
    public GetDomainUaAclResponse getDomainUaAcl(GetDomainUaAclRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest =
                createRequest(request, HttpMethodName.GET, DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("uaAcl", "");
        return invokeHttpClient(internalRequest, GetDomainUaAclResponse.class);
    }

    /**
     * set cors config of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainCors operation returned by the service.
     */
    public CommonResponse setDomainCors(SetDomainCorsRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cors", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }


    /**
     * Get Cors config information of a domain
     *
     * @param domain domain's name
     * @return Result of the getDomainCors operation returned by the service.
     */
    public GetDomainCorsResponse getDomainCors(String domain) {
        return getDomainCors(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get Cors config information of a domain
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainCors operation returned by the service.
     */
    public GetDomainCorsResponse getDomainCors(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("cors", "");
        return invokeHttpClient(internalRequest, GetDomainCorsResponse.class);
    }

    /**
     * set AccessLimit config of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainAccessLimit operation returned by the service.
     */
    public CommonResponse setDomainAccessLimit(SetDomainAccessLimitRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("accessLimit", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * set origin protocal config of specified domain acceleration.
     *
     * @param request
     * @return
     */
    public CommonResponse setDomainOriginProtocol(SetDomainOriginProtocolRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("originProtocol", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get origin protocol config information of specified domain acceleration.
     *
     * @param domain
     * @return
     */
    public GetDomainOriginProtocolResponse getDomainOriginProtocol(String domain) {
        GetDomainOriginProtocolRequest request = new GetDomainOriginProtocolRequest().withDomain(domain);
        return this.getDomainOriginProtocol(request);
    }

    /**
     * Get origin protocol config information of specified domain acceleration.
     *
     * @param request
     * @return
     */
    public GetDomainOriginProtocolResponse getDomainOriginProtocol(GetDomainOriginProtocolRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("originProtocol", "");
        return invokeHttpClient(internalRequest, GetDomainOriginProtocolResponse.class);
    }

    /**
     * Get AccessLimit config information of specified domain acceleration.
     *
     * @param domain domain's name
     * @return Result of the getDomainAccessLimit operation returned the domain.
     */
    public GetDomainAccessLimitResponse getDomainAccessLimit(String domain) {
        return getDomainAccessLimit(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get AccessLimit config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainAccessLimit operation returned by the service.
     */
    public GetDomainAccessLimitResponse getDomainAccessLimit(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("accessLimit", "");
        return invokeHttpClient(internalRequest, GetDomainAccessLimitResponse.class);
    }

    /**
     * set ClientIp config of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the setDomainClientIp operation returned by the service.
     */
    public CommonResponse setDomainClientIp(SetDomainClientIpRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("clientIp", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    public GetDomainClientIpResponse getDomainClientIp(String domain) {
        return getDomainClientIp(new DomainMiddleRequest().withDomain(domain));
    }

    /**
     * Get ClientIp config information of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the domain.
     * @return Result of the getDomainClientIp operation returned by the service.
     */
    public GetDomainClientIpResponse getDomainClientIp(DomainMiddleRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.GET,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("clientIp", "");
        return invokeHttpClient(internalRequest, GetDomainClientIpResponse.class);
    }

    /**
     * Set HTTPS with certain configuration.
     *
     * @param domain Name of the domain.
     * @param https  The configuration of HTTPS.
     */
    public void setHttpsConfig(String domain, HttpsConfig https) {
        SetHttpsConfigRequest request = new SetHttpsConfigRequest()
                .withDomain(domain)
                .withHttps(https);
        setHttpsConfig(request);
    }

    /**
     * Set HTTPS with certain configuration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setHTTPSAcceleration operation returned by the service.
     */
    public CommonResponse setHttpsConfig(SetHttpsConfigRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest =
                createRequest(request, HttpMethodName.PUT, DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("https", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Set the request authentication.
     *
     * @param domain      Name of the domain.
     * @param requestAuth The configuration of authentication.
     */
    public void setRequestAuth(String domain, RequestAuth requestAuth) {
        SetRequestAuthRequest request = new SetRequestAuthRequest()
                .withDomain(domain)
                .withRequestAuth(requestAuth);
        setRequestAuth(request);
    }

    /**
     * Set the request authentication.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setHTTPSAcceleration operation returned by the service.
     */
    public CommonResponse setRequestAuth(SetRequestAuthRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest =
                createRequest(request, HttpMethodName.PUT, DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("requestAuth", "");
        this.attachRequestToBody(request, internalRequest);
        return invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Post prefetch request
     *
     * @param url The URL to be prefetched.
     * @return Result of the prefetch operation returned by the service.
     */
    public PrefetchResponse prefetch(String url) {
        return prefetch(new PrefetchRequest().addTask(new PrefetchTask().withUrl(url)));
    }

    /**
     * Post prefetch request
     *
     * @param request The request containing all of the URLs to be prefetched.
     * @return Result of the prefetch operation returned by the service.
     */
    public PrefetchResponse prefetch(PrefetchRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.POST,
                CACHE, "prefetch");
        this.attachRequestToBody(request, internalRequest);
        return this.invokeHttpClient(internalRequest, PrefetchResponse.class);
    }

    /**
     * Post purge request
     *
     * @param url The URL to be purged.
     * @return Result of the purge operation returned by the service.
     */
    public PurgeResponse purge(String url) {
        return purge(new PurgeRequest().addTask(new PurgeTask().withUrl(url)));
    }

    /**
     * Post purge request
     *
     * @param directory The directory to be purged.
     * @return Result of the purge operation returned by the service.
     */
    public PurgeResponse purgeDirectory(String directory) {
        return purge(new PurgeRequest().addTask(new PurgeTask().withDirectory(directory)));
    }

    /**
     * Post purge request
     *
     * @param request The request containing all of the URLs to be purged.
     * @return Result of the purge operation returned by the service.
     */
    public PurgeResponse purge(PurgeRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.POST,
                CACHE, "purge");
        this.attachRequestToBody(request, internalRequest);
        return this.invokeHttpClient(internalRequest, PurgeResponse.class);
    }

    /**
     * Get purge status with specified attributes.
     *
     * @param request The request containing the task id returned by purge operation.
     * @return Details of tasks
     */
    public GetPurgeStatusResponse getPurgeStatus(GetPurgeStatusRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                CACHE, "purge");
        if (request.getId() != null) {
            internalRequest.addParameter("id", request.getId());
        }

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getMarker() != null) {
            internalRequest.addParameter("marker", request.getMarker());
        }

        if (request.getUrl() != null) {
            internalRequest.addParameter("url", request.getUrl());
        }

        return this.invokeHttpClient(internalRequest, GetPurgeStatusResponse.class);
    }

    /**
     * Get prefetch status with specified attributes.
     *
     * @param request The request containing the task id returned by prefetch operation.
     * @return Details of tasks
     */
    public GetPrefetchStatusResponse getPrefetchStatus(GetPrefetchStatusRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                CACHE, "prefetch");
        if (request.getId() != null) {
            internalRequest.addParameter("id", request.getId());
        }

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getMarker() != null) {
            internalRequest.addParameter("marker", request.getMarker());
        }

        if (request.getUrl() != null) {
            internalRequest.addParameter("url", request.getUrl());
        }

        return this.invokeHttpClient(internalRequest, GetPrefetchStatusResponse.class);
    }

    /**
     * Get pv statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatPvResponse getStatPv(GetStatPvRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET, STAT, "pv");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        if (request.isWithRegion()) {
            internalRequest.addParameter("withRegion", "");
        }
        return this.invokeHttpClient(internalRequest, GetStatPvResponse.class);
    }

    /**
     * Get flow statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatFlowResponse getStatFlow(GetStatFlowRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "flow");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        if (request.isWithRegion()) {
            internalRequest.addParameter("withRegion", "");
        }
        return this.invokeHttpClient(internalRequest, GetStatFlowResponse.class);
    }

    /**
     * Get origin flow statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatSrcFlowResponse getStatSrcFlow(GetStatSrcFlowRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "srcflow");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        return this.invokeHttpClient(internalRequest, GetStatSrcFlowResponse.class);
    }

    /**
     * Get hit rate statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatHitRateResponse getStatHitRate(GetStatHitRateRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "hitrate");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        return this.invokeHttpClient(internalRequest, GetStatHitRateResponse.class);
    }

    /**
     * Get http code statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatHttpCodeResponse getStatHttpCode(GetStatHttpCodeRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "httpcode");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        if (request.isWithRegion()) {
            internalRequest.addParameter("withRegion", "");
        }

        return this.invokeHttpClient(internalRequest, GetStatHttpCodeResponse.class);
    }

    /**
     * Get top url statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatTopUrlResponse getStatTopUrl(GetStatTopUrlRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "topn", "url");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime", DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime", DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        return this.invokeHttpClient(internalRequest, GetStatTopUrlResponse.class);
    }

    /**
     * Get top http referer statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatTopRefererResponse getStatTopReferer(GetStatTopRefererRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "topn", "referer");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        return this.invokeHttpClient(internalRequest, GetStatTopRefererResponse.class);
    }

    /**
     * Get uv statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatUvResponse getStatUv(GetStatUvRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET, STAT, "uv");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        return this.invokeHttpClient(internalRequest, GetStatUvResponse.class);
    }

    /**
     * Get average speed statistics with specified attributes.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatAvgSpeedResponse getStatAvgSpeed(GetStatAvgSpeedRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                STAT, "avgspeed");

        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }

        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }

        if (request.getDomain() != null) {
            internalRequest.addParameter("domain", request.getDomain());
        }

        if (request.getPeriod() != null) {
            internalRequest.addParameter("period", String.valueOf(request.getPeriod()));
        }

        return this.invokeHttpClient(internalRequest, GetStatAvgSpeedResponse.class);
    }

    /**
     * Get cache operation quota.
     *
     * @return Details of statistics
     */
    public GetCacheQuotaResponse getCacheQuota() {
        GetCacheQuotaRequest request = new GetCacheQuotaRequest();
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                CACHE, "quota");
        return this.invokeHttpClient(internalRequest, GetCacheQuotaResponse.class);
    }

    /**
     * Get cache operation quota.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetCacheQuotaResponse getCacheQuota(GetCacheQuotaRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET, CACHE, "quota");
        return this.invokeHttpClient(internalRequest, GetCacheQuotaResponse.class);
    }

    /**
     * Get cache operation records.
     *
     * @param request The request containing all the options related to the CacheRecords.
     * @return Details of CacheRecords
     */
    public GetCacheRecordsResponse getCacheRecords(GetCacheDetailRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                CACHE, "records");

        this.validateAndFillRequestUrl(internalRequest, request.getType(), request.getStartTime(),
                request.getEndTime(), request.getUrl(), request.getMarker());
        return this.invokeHttpClient(internalRequest, GetCacheRecordsResponse.class);
    }

    /**
     * Update dsa service of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDsa operation returned by the service.
     */
    public void setDsa(SetDsaRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.PUT, "dsa");
        this.attachRequestToBody(request, internalRequest);
        this.invokeHttpClient(internalRequest, CommonResponse.class);
    }

    /**
     * Get Dsa Domain List.
     *
     * @return Details of DsaDomain
     */
    public GetDsaDomainListResponse getDsaDomainList() {
        CdnRequest request = new CdnRequest();
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET, "dsa", DOMAIN);
        return this.invokeHttpClient(internalRequest, GetDsaDomainListResponse.class);
    }

    /**
     * Update Dsa rules of specified domain acceleration.
     *
     * @param request The request containing all of the options related to the update request.
     * @return Result of the setDomainDsa operation returned by the service.
     */
    public void setDomainDsa(SetDomainDsaRequest request) {
        Validate.checkNotNull(request, "The parameter request should NOT be null.");
        InternalRequest internalRequest = createRequest(request, HttpMethodName.PUT,
                DOMAIN, request.getDomain(), "config");
        internalRequest.addParameter("dsa", "");
        this.attachRequestToBody(request, internalRequest);
        invokeHttpClient(internalRequest, CdnResponse.class);
    }


    /**
     * Get URLs of logmodel files
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetDomainLogResponse getDomainLog(GetDomainLogRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET,
                LOG, request.getDomain(), "logmodel");
        if (request.getStartTime() != null) {
            internalRequest.addParameter("startTime",
                    DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }
        if (request.getEndTime() != null) {
            internalRequest.addParameter("endTime",
                    DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }
        return this.invokeHttpClient(internalRequest, GetDomainLogResponse.class);
    }

    /**
     * Get multiple domain URLs of logmodel files
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetDomainListLogResponse getDomainListLog(GetDomainListLogRequest request) {
        GetDomainListLogTransRequest transRequest = new GetDomainListLogTransRequest();

        if (request.getStartTime() != null) {
            transRequest.withStartTime(DateUtils.formatAlternateIso8601Date(request.getStartTime()));
        }
        if (request.getEndTime() != null) {
            transRequest.withEndTime(DateUtils.formatAlternateIso8601Date(request.getEndTime()));
        }
        transRequest.withDomains(request.getDomains()).withType(request.getType())
                .withPageNo(request.getPageNo()).withPageSize(request.getPageSize());

        InternalRequest internalRequest = this.createRequest(transRequest, HttpMethodName.POST,
                LOG, "list");
        this.attachRequestToBody(transRequest, internalRequest);
        return this.invokeHttpClient(internalRequest, GetDomainListLogResponse.class);
    }


    /**
     * Get the description of certain IP address.
     *
     * @param ip IP address.
     * @return Details of statistics
     */
    public DescribeIpResponse describeIp(String ip) {
        DescribeIpRequest request = new DescribeIpRequest()
                .withIp(ip);
        return describeIp(request);
    }

    /**
     * Get the description of certain IP address.
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public DescribeIpResponse describeIp(DescribeIpRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.GET, UTILS);
        checkNotNull(request.getIp());
        internalRequest.addParameter("action", request.getAction());
        internalRequest.addParameter("ip", request.getIp());

        return this.invokeHttpClient(internalRequest, DescribeIpResponse.class);
    }

    /**
     * Get statistics metric with specified attributes (stat_version_2.0).
     *
     * @param request The request containing all the options related to the statistics.
     * @return Details of statistics
     */
    public GetStatMetricResponse getStatMetricData(GetStatMetricRequest request) {
        InternalRequest internalRequest = this.createRequest(request, HttpMethodName.POST, STAT, "/query");
        // this.attachRequestToBody(request, internalRequest);
        byte[] content;
        try {
            Map<String, Object> params = request.toMap();
            // In order to be compatible with the interface's old parameter, which was not be in camel-style.
            params.put("key_type", request.getKeyType());
            content = JsonUtils.toJsonString(params).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new BceClientException("utf-8 encoding not supported!", e);
        }
        internalRequest.addHeader(Headers.CONTENT_LENGTH, String.valueOf(content.length));
        internalRequest.addHeader(Headers.CONTENT_TYPE, "application/json; charset=utf-8");
        internalRequest.setContent(RestartableInputStream.wrap(content));
        return this.invokeHttpClient(internalRequest, GetStatMetricResponse.class);
    }

    /**
     * Creates and initializes a new request object for the specified resource.
     *
     * @param bceRequest    The original BCE request created by the user.
     * @param httpMethod    The HTTP method to use when sending the request.
     * @param pathVariables The optional variables used in the URI path.
     * @return A new request object populated with endpoint, resource path and specific
     * parameters to send.
     */
    private InternalRequest createRequest(
            AbstractBceRequest bceRequest, HttpMethodName httpMethod, String... pathVariables) {
        List<String> path = new ArrayList<String>();
        path.add(VERSION);

        if (pathVariables != null) {
            for (String pathVariable : pathVariables) {
                path.add(pathVariable);
            }
        }

        URI uri = HttpUtils.appendUri(this.getEndpoint(), path.toArray(new String[path.size()]));
        InternalRequest request = new InternalRequest(httpMethod, uri);
        SignOptions signOptions = new SignOptions();
        signOptions.setHeadersToSign(new HashSet<String>(Arrays.asList(HEADERS_TO_SIGN)));
        request.setSignOptions(signOptions);
        request.setCredentials(bceRequest.getRequestCredentials());

        return request;
    }

    /**
     * put json object into http content for put or post request.
     *
     * @param request     json object of rest request
     * @param httpRequest http request object
     */
    private void attachRequestToBody(AbstractBceRequest request, InternalRequest httpRequest) {
        byte[] content;
        try {
            content = JsonUtils.toJsonString(request).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new BceClientException("utf-8 encoding not supported!", e);
        }
        httpRequest.addHeader(Headers.CONTENT_LENGTH, String.valueOf(content.length));
        httpRequest.addHeader(Headers.CONTENT_TYPE, "application/json; charset=utf-8");
        httpRequest.setContent(RestartableInputStream.wrap(content));
    }

    private void validateAndFillRequestUrl(InternalRequest internalRequest,
                                           String type, Date startTime, Date endTime,
                                           String url, String marker) {
        if (type != null) {
            internalRequest.addParameter("type", type);
        }

        if (startTime != null) {
            internalRequest.addParameter("startTime", DateUtils.formatAlternateIso8601Date(startTime));
        }

        if (endTime != null) {
            internalRequest.addParameter("endTime", DateUtils.formatAlternateIso8601Date(endTime));
        }

        if (url != null) {
            internalRequest.addParameter("url", url);
        }

        if (marker != null) {
            internalRequest.addParameter("marker", String.valueOf(marker));
        }

    }
}
