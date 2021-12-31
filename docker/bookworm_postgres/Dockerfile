FROM postgres:14.1-alpine

RUN apk update && apk add tzdata
RUN cp /usr/share/zoneinfo/Europe/Warsaw /etc/localtime
RUN rm -r /usr/share/zoneinfo/Africa && \
    rm -r /usr/share/zoneinfo/Antarctica && \
    rm -r /usr/share/zoneinfo/Arctic && \
    rm -r /usr/share/zoneinfo/Asia && \
    rm -r /usr/share/zoneinfo/Atlantic && \
    rm -r /usr/share/zoneinfo/Australia && \
    rm -r /usr/share/zoneinfo/America  && \
    rm -r /usr/share/zoneinfo/Indian && \
    rm -r /usr/share/zoneinfo/Mexico && \
    rm -r /usr/share/zoneinfo/Pacific && \
    rm -r /usr/share/zoneinfo/Chile && \
    rm -r /usr/share/zoneinfo/Canada
RUN echo "Europe/Warsaw" >  /etc/timezone

ENV TZ Europe/Warsaw
ENV LANG pl_PL.UTF-8
ENV LANGUAGE pl_PL.UTF-8
ENV LC_ALL pl_PL.UTF-8
