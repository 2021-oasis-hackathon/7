import googlemaps

gmaps = googlemaps.Client(key='AIzaSyC3a8Nl31LCuwBbtXzzYazsz36MhTywyE4')
reverse_geocode_result = gmaps.reverse_geocode(
    (34.81481207887022, 126.424530487225),language='ko'
    ) # 수정필요
B=list(reverse_geocode_result[0].values())
print(B[1]) #확인용 삭제
place=B[1].split() # B[1] - 풀네임 - 삭제 
if '광역시' in place[1]:
    locate=place[1]
else:
    locate=place[2]
f1=open("locate.txt",'w')
f1.write(locate)
f1.close
print(locate) # 확인용 - 삭제
#34.81481207887022, 126.424530487225 - 목포시
#34.801402985089254, 126.6221388943585 - 영암군
#35.16322415631088, 126.7543091789832 - 광주광역시