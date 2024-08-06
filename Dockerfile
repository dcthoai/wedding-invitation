FROM tomcat:9.0.86

# Xóa ứng dụng web mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR vào thư mục webapps của Tomcat
COPY *.war /usr/local/tomcat/webapps/

# Expose the required port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
